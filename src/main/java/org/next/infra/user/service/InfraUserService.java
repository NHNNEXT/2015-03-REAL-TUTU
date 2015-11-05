package org.next.infra.user.service;

import org.next.infra.broker.SessionBroker;
import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.*;
import org.next.infra.user.dto.UserDto;
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
        return new CommonJsonResponse(ResponseCode.SUCCESS, new UserDto(user));
    }

    public CommonJsonResponse getUser(Long id) {
        LoginAccount user = loginAccountRepository.getOne(id);
        if (user == null)
            return new CommonJsonResponse(ResponseCode.GetSessionUser.EMPTY);
        return new CommonJsonResponse(ResponseCode.SUCCESS, new UserSummaryDto(user.getUserInfo()));
    }


    public CommonJsonResponse login(LoginToken loginToken, HttpSession session) {
        LoginAccount dbAccount = getByEmailId(loginToken);

        if (dbAccount == null)
            return new CommonJsonResponse(ResponseCode.Login.NOT_EXIST_EMAIL);


        if (dbAccount.isWithDrawalAccount())
            return new CommonJsonResponse(ResponseCode.Login.WITHDRAWAL_ACCOUNT);


        if (!loginToken.isPasswordCorrect(dbAccount.getPassword())) {
            return new CommonJsonResponse(ResponseCode.Login.WRONG_PASSWORD);
        }

        setLoginStatus(dbAccount, loginToken);
        sessionBroker.setLoginAccountId(session, dbAccount.getId());
        return new CommonJsonResponse(ResponseCode.SUCCESS, new UserDto(dbAccount));
    }

    public CommonJsonResponse join(LoginToken loginToken, String name) {
        if (alreadyJoined(loginToken)) {
            return new CommonJsonResponse(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }
        loginToken.encryptPassword();
        LoginAccount account = loginAccount(loginToken);

        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfoRepository.save(userInfo);

        account.setUserInfo(userInfo);
        loginAccountRepository.save(account);

        return new CommonJsonResponse(ResponseCode.SUCCESS);
    }

    public CommonJsonResponse updateUserInfo(UserInfo passed, HttpSession session) {
        LoginAccount dbAccount = userInfoBroker.getLoginAccount(session);
        if (dbAccount == null)
            return new CommonJsonResponse(ResponseCode.GetSessionUser.EMPTY);
        UserInfo userinfo = dbAccount.getUserInfo();
        userinfo.update(passed);
        userInfoRepository.save(userinfo);
        return successJsonResponse(new UserSummaryDto(userinfo));
    }

    public CommonJsonResponse withdrawal(HttpSession session) {
        LoginAccount loginAccount = userInfoBroker.getLoginAccount(session);
        loginAccount.setState(AccountStateType.WITHDRAWAL);
        return successJsonResponse();
    }
//
//    private void updateAccount(LoginAccount dbAccount, LoginToken loginToken) {
//        loginToken.encryptPassword();
//        dbAccount.setLoginToken(loginToken);
//    }

//    private void updateUserInfo(LoginAccount loginAccount, UserInfo userInfo) {
//        UserInfo dbUserInfo = loginAccount.getUserInfo();
//
//        if (notNull(dbUserInfo)) {
//            dbUserInfo.setName(userInfo.getName());
//            dbUserInfo.setMajor(userInfo.getMajor());
//            dbUserInfo.setProfileUrl(userInfo.getProfileUrl());
//            dbUserInfo.setPhoneNumber(userInfo.getPhoneNumber());
//            dbUserInfo.setStudentId(userInfo.getStudentId());
//        } else {
//            loginAccount.setUserInfo(userInfo);
//            userInfoRepository.save(userInfo);
//        }
//    }

    private void setLoginStatus(LoginAccount dbAccount, LoginToken loginToken) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginToken.getEmail(), loginToken.getPassword(), getAuthorities(dbAccount));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getAuthorities(LoginAccount dbAccount) {
        return dbAccount.getUserAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityType().toString())).collect(Collectors.toList());
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