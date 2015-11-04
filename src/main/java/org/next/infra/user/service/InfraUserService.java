package org.next.infra.user.service;

import org.next.infra.broker.SessionBroker;
import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.*;
import org.next.infra.user.dto.ClientUserInfoDto;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.repository.AuthorityRepository;
import org.next.infra.user.repository.LoginAccountRepository;
import org.next.infra.user.repository.UserInfoRepository;
import org.next.lms.dto.UserSummaryDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SessionBroker sessionBroker;

    @Autowired
    private UserInfoBroker userInfoBroker;

    public CommonJsonResponse getSessionUser(HttpSession session) {
        LoginAccount user = userInfoBroker.getLoginAccount(session);
        if (user == null)
            return new CommonJsonResponse(ResponseCode.GetSessionUser.EMPTY);
        return new CommonJsonResponse(ResponseCode.SUCCESS, new ClientUserInfoDto(user));
    }


    public CommonJsonResponse login(LoginToken loginToken, HttpSession session) {
        LoginAccount dbAccount = getByEmailId(loginToken);

        if (dbAccount == null)
            return new CommonJsonResponse(ResponseCode.Login.NOT_EXIST_EMAIL);


        if (withDrawalAccount(dbAccount))
            return new CommonJsonResponse(ResponseCode.Login.WITHDRAWAL_ACCOUNT);


        if (!passwordCorrect(dbAccount, loginToken)) {
            return new CommonJsonResponse(ResponseCode.Login.WRONG_PASSWORD);
        }

        setLoginStatus(dbAccount, loginToken);
        sessionBroker.setLoginAccountId(session, dbAccount.getId());
        return new CommonJsonResponse(ResponseCode.SUCCESS, new ClientUserInfoDto(dbAccount));
    }

    public CommonJsonResponse join(LoginToken loginToken) {
        if (alreadyJoined(loginToken)) {
            return new CommonJsonResponse(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }
        encodePassword(loginToken);
        LoginAccount account = loginAccount(loginToken);

        UserInfo userInfo = new UserInfo();
        userInfoRepository.save(userInfo);

        account.setUserInfo(userInfo);
        loginAccountRepository.save(account);

        return new CommonJsonResponse(ResponseCode.SUCCESS);
    }

    public CommonJsonResponse edit(LoginToken loginToken, UserInfo userInfo, HttpSession session) {
        LoginAccount dbAccount = userInfoBroker.getLoginAccount(session);
        if (notNull(loginToken)) {
            updateAccount(dbAccount, loginToken);
            updateUserInfo(dbAccount, userInfo);
        }
        return successJsonResponse();
    }

    public CommonJsonResponse withdrawal(HttpSession session) {
        LoginAccount loginAccount = userInfoBroker.getLoginAccount(session);
        loginAccount.setState(AccountStateType.WITHDRAWAL);
        return successJsonResponse();
    }

    private void updateAccount(LoginAccount dbAccount, LoginToken loginToken) {
        encodePassword(loginToken);
        dbAccount.setEmailId(loginToken.getEmail());
        dbAccount.setPassword(loginToken.getPassword());
    }

    private void updateUserInfo(LoginAccount loginAccount, UserInfo userInfo) {
        UserInfo dbUserInfo = loginAccount.getUserInfo();

        if (notNull(dbUserInfo)) {
            dbUserInfo.setName(userInfo.getName());
            dbUserInfo.setMajor(userInfo.getMajor());
            dbUserInfo.setPhoneNumber(userInfo.getPhoneNumber());
            dbUserInfo.setStudentId(userInfo.getStudentId());
        } else {
            loginAccount.setUserInfo(userInfo);
            userInfoRepository.save(userInfo);
        }
    }

    private boolean passwordCorrect(LoginAccount dbAccount, LoginToken loginToken) {
        return dbAccount != null && passwordEncoder.matches(loginToken.getPassword(), dbAccount.getPassword());
    }

    private void setLoginStatus(LoginAccount dbAccount, LoginToken loginToken) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginToken.getEmail(), loginToken.getPassword(), getAuthorities(dbAccount));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getAuthorities(LoginAccount dbAccount) {
        return dbAccount.getUserAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityType().toString())).collect(Collectors.toList());
    }

    private boolean withDrawalAccount(LoginAccount dbAccount) {
        return dbAccount != null && (dbAccount.getState() == AccountStateType.WITHDRAWAL || dbAccount.getState() != AccountStateType.ACTIVE);
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
        LoginAccount dbAccount = getByEmailId(loginToken);
        return dbAccount != null;
    }

    private LoginAccount getByEmailId(LoginToken loginToken) {
        return loginAccountRepository.findByEmailId(loginToken.getEmail());
    }

    public List<UserSummaryDto> findByNameLike(String keyword) {
        List<UserSummaryDto> result = new ArrayList<>();
        userInfoRepository.findByNameContaining(keyword).forEach(userInfo -> result.add(new UserSummaryDto(userInfo)));
        return result;
    }

}