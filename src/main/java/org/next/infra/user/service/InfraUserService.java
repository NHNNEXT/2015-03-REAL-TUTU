package org.next.infra.user.service;

import org.next.infra.common.dto.JsonResponse;
import org.next.infra.user.domain.*;
import org.next.infra.user.dto.ClientUserInfoDto;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.repository.AuthorityRepository;
import org.next.infra.user.repository.LoginAccountRepository;
import org.next.infra.user.repository.UserInfoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
@Transactional
public class InfraUserService {
    private static final Logger logger = LoggerFactory.getLogger(InfraUserService.class);

    @Autowired
    private LoginAccountRepository loginAccountRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JsonResponse login(LoginToken loginToken, HttpSession session) {
        LoginAccount dbAccount = findAccount(loginToken);

        if(dbAccount == null) {
            return new JsonResponse().setErr("존재하지 않는 계정입니다.");
        }

        if(loginAbleAccount(dbAccount)) {
            List<GrantedAuthority> authorities = dbAccount.getUserAuthorities().stream().map(authority -> authority.getAuthority().getAuthorityType())
                    .map(authorityType -> new SimpleGrantedAuthority(authorityType.toString()))
                    .collect(Collectors.toList());

            Authentication token = new UsernamePasswordAuthenticationToken(loginToken.getEmail(), loginToken.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
            session.setAttribute("loginAccountId", dbAccount.getId());
            session.setAttribute("userInfo", dbAccount.getUserInfo());

            return new JsonResponse().setResult(new ClientUserInfoDto(dbAccount));
        }

        if(holdAccount(dbAccount)) {
            return new JsonResponse().setErr("일시중지된 계정입니다.");
        }

        if(withDrawalAccount(dbAccount)) {
            return new JsonResponse().setErr("탈퇴한 계정입니다.");
        }

        return new JsonResponse().setErr("아이디 또는 비밀번호를 확인해 주세요.");
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

    private boolean holdAccount(LoginAccount dbAccount) {
        return dbAccount != null && dbAccount.getState() == AccountStateType.HOLD;
    }

    private boolean withDrawalAccount(LoginAccount dbAccount) {
        return dbAccount != null && dbAccount.getState() == AccountStateType.WITHDRAWAL;
    }

    public JsonResponse join(LoginToken loginToken, AccountType accountType, UserInfo userInfo) {
        encodePassword(loginToken);
        userInfoRepository.save(userInfo);
        loginAccountRepository.save(loginAccount(loginToken, accountType, userInfo));
        return new JsonResponse().setResult("Success");
    }

    private void encodePassword(LoginToken loginToken) {
        String bcryptedPassword = passwordEncoder.encode(loginToken.getPassword());
        loginToken.setPassword(bcryptedPassword);
    }

    private LoginAccount loginAccount(LoginToken loginToken, AccountType accountType, UserInfo userInfo) {
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setLoginToken(loginToken);
        loginAccount.setState(AccountStateType.ACTIVE);
        loginAccount.setType(accountType);
        loginAccount.setUserInfo(userInfo);
        loginAccount.addAuthority(getAuthority(AuthorityType.ROLE_STUDENT));    // TODO 수정필요
        loginAccount.addAuthority(getAuthority(AuthorityType.ROLE_PROFESSOR));  // TODO 수정필요
        return loginAccount;
    }

    private Authority getAuthority(AuthorityType authorityType) {
        return authorityRepository.findByAuthorityType(authorityType);
    }

    public JsonResponse getUserInfo(HttpSession session) {
        LoginAccount loginAccount = loginAccountRepository.findOne((Long) session.getAttribute("loginAccountId"));
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if(loginAccount.getState() == AccountStateType.WITHDRAWAL) {
            return null;
        }

        loginAccount.setUserInfo(userInfo);

        return new JsonResponse().setResult(new ClientUserInfoDto(loginAccount));
    }

    public void edit(LoginAccount loginAccount){
        if(loginAccount.getUserInfo() == null) {
            throw new NullPointerException("User Info가 설정되지 않음");
        }
        loginAccountRepository.save(loginAccount);
    }

    public JsonResponse withdrawal(HttpSession session) {
        Long accountId = (Long) session.getAttribute("loginAccountId");
        LoginAccount account = loginAccountRepository.getOne(accountId);
        account.setState(AccountStateType.WITHDRAWAL);
        return new JsonResponse().setResult("Success");
    }
}