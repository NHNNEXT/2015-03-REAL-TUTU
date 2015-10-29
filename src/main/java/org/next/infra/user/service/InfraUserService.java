package org.next.infra.user.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.*;
import org.next.infra.user.dto.ClientUserInfoDto;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.repository.AuthorityRepository;
import org.next.infra.user.repository.LoginAccountRepository;
import org.next.infra.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.common.dto.CommonJsonResponse.errorJsonResponse;
import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.notNull;

@Service
@Transactional
public class InfraUserService {

    @Autowired
    private LoginAccountRepository loginAccountRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CommonJsonResponse login(LoginToken loginToken, HttpSession session) {
        LoginAccount dbAccount = findAccount(loginToken);

        if(dbAccount == null) {
            return errorJsonResponse("아이디 또는 비밀번호를 확인해 주세요.");
        }

        if(loginAbleAccount(dbAccount)) {
            setLoginStatus(loginToken, dbAccount, session);
            return successJsonResponse(new ClientUserInfoDto(dbAccount));
        }

        if(holdAccount(dbAccount)) {
            return errorJsonResponse("일시중지된 계정입니다.");
        }

        if(withDrawalAccount(dbAccount)) {
            return errorJsonResponse("탈퇴한 계정입니다.");
        }

        return errorJsonResponse();
    }

    public CommonJsonResponse join(LoginToken loginToken) {
        if(alreadyJoined(loginToken)) {
            return errorJsonResponse("이미 가입되어 있는 계정입니다.");
        }
        encodePassword(loginToken);
        loginAccountRepository.save(loginAccount(loginToken));
        return successJsonResponse();
    }

    public CommonJsonResponse getUserInfo(HttpSession session) {
        LoginAccount loginAccount = loginAccountRepository.findOne((Long) session.getAttribute("loginAccountId"));

        if(loginAccount.getState() == AccountStateType.WITHDRAWAL) {
            return errorJsonResponse("탈퇴된 회원입니다.");
        }

        return successJsonResponse(new ClientUserInfoDto(loginAccount));
    }

    public CommonJsonResponse edit(LoginToken loginToken, UserInfo userInfo, HttpSession session){
        LoginAccount dbAccount = loginAccountRepository.findOne((Long) session.getAttribute("loginAccountId"));

        if(notNull(loginToken)) {
            updateAccount(dbAccount, loginToken);
            updateUserInfo(dbAccount, userInfo);
        }

        return successJsonResponse();
    }

    public CommonJsonResponse withdrawal(HttpSession session) {
        Long accountId = (Long) session.getAttribute("loginAccountId");
        LoginAccount account = loginAccountRepository.getOne(accountId);
        account.setState(AccountStateType.WITHDRAWAL);
        return successJsonResponse();
    }

    private void updateAccount(LoginAccount dbAccount, LoginToken loginToken) {
        encodePassword(loginToken);
        dbAccount.setEmailId(loginToken.getEmail());
        dbAccount.setPassword(loginToken.getPassword());
    }

    private void updateUserInfo(LoginAccount loginAccount, UserInfo userInfo) {
        UserInfo dbUserInfo = loginAccount.getUserInfo();

        if(notNull(dbUserInfo)) {
            dbUserInfo.setMajor(userInfo.getMajor());
            dbUserInfo.setName(userInfo.getName());
            dbUserInfo.setPhoneNumber(userInfo.getPhoneNumber());
            dbUserInfo.setStudentId(userInfo.getStudentId());
        } else {
            loginAccount.setUserInfo(userInfo);
            userInfoRepository.save(userInfo);
        }
    }

    private LoginAccount findAccount(LoginToken loginToken) {
        LoginAccount dbAccount = loginAccountRepository.findByEmailId(loginToken.getEmail());
        if(dbAccount != null && passwordEncoder.matches(loginToken.getPassword(), dbAccount.getPassword())) {
            return dbAccount;
        }
        return null;
    }

    private boolean loginAbleAccount(LoginAccount dbAccount) {
        return dbAccount != null && dbAccount.getState() == AccountStateType.ACTIVE;
    }

    private void setLoginStatus(LoginToken loginToken, LoginAccount dbAccount, HttpSession session) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginToken.getEmail(), loginToken.getPassword(), getAuthorities(dbAccount));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("loginAccountId", dbAccount.getId());
    }

    private List<GrantedAuthority> getAuthorities(LoginAccount dbAccount) {
        return dbAccount.getUserAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityType().toString())).collect(Collectors.toList());
    }

    private boolean holdAccount(LoginAccount dbAccount) {
        return dbAccount != null && dbAccount.getState() == AccountStateType.HOLD;
    }

    private boolean withDrawalAccount(LoginAccount dbAccount) {
        return dbAccount != null && dbAccount.getState() == AccountStateType.WITHDRAWAL;
    }

    private void encodePassword(LoginToken loginToken) {
        String encodedPassword = passwordEncoder.encode(loginToken.getPassword());
        loginToken.setPassword(encodedPassword);
    }

    private LoginAccount loginAccount(LoginToken loginToken) {
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setLoginToken(loginToken);
        loginAccount.setState(AccountStateType.ACTIVE);
        loginAccount.addAuthority(getAuthority(AuthorityType.ROLE_NOT_AUTHORIZED));
        return loginAccount;
    }

    private Authority getAuthority(AuthorityType authorityType) {
        return authorityRepository.findByAuthorityType(authorityType);
    }

    private boolean alreadyJoined(LoginToken loginToken) {
        LoginAccount dbAccount = loginAccountRepository.findByEmailId(loginToken.getEmail());
        return dbAccount != null;
    }
}