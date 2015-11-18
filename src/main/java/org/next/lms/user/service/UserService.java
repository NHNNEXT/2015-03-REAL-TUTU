package org.next.lms.user.service;

import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.*;
import org.next.lms.user.dto.UserDto;
import org.next.lms.user.dto.UserPageDto;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.dto.UserSummaryDto;
import org.next.lms.user.state.AccountState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.next.infra.view.JsonView.successJsonResponse;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JsonView getSessionUser(HttpSession session) {
        User user = sessionUtil.getUser(session);
        if (user == null)
            return new JsonView(ResponseCode.GetSessionUser.EMPTY);
        return new JsonView(ResponseCode.SUCCESS, new UserDto(user));
    }

    public JsonView getUser(String id) {
        User user;
        try {
            Long longId = Long.parseLong(id);
            user = userRepository.findOne(longId);
        } catch (NumberFormatException e) {
            user = userRepository.findByEmail(id);
        }
        if (user == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);
        return new JsonView(ResponseCode.SUCCESS, new UserPageDto(user));
    }


    public JsonView login(User user, HttpSession session) {
        User dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser == null)
            return new JsonView(ResponseCode.Login.NOT_EXIST_EMAIL);

        if (AccountState.WITHDRAWAL.equals(dbUser.getState()))
            return new JsonView(ResponseCode.Login.WITHDRAWAL_ACCOUNT);

        if (!user.isPasswordCorrect(passwordEncoder, dbUser.getPassword())) {
            return new JsonView(ResponseCode.Login.WRONG_PASSWORD);
        }
        sessionUtil.setUserIdToSession(session, dbUser.getId());
        return new JsonView(ResponseCode.SUCCESS, new UserDto(dbUser));
    }

    public JsonView register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new JsonView(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }
        user.encryptPassword(passwordEncoder);
        userRepository.save(user);

        return new JsonView(ResponseCode.SUCCESS);
    }

    public JsonView updateUser(User passed, HttpSession session) {
        User dbAccount = sessionUtil.getLoggedUser(session);
        if (dbAccount == null)
            return new JsonView(ResponseCode.GetSessionUser.EMPTY);
        dbAccount.update(passed);
        userRepository.save(dbAccount);
        return successJsonResponse(new UserSummaryDto(dbAccount));
    }

    public JsonView withdrawal(HttpSession session) {
        User user = sessionUtil.getLoggedUser(session);
        user.setState(AccountState.WITHDRAWAL);
        return successJsonResponse();
    }

//    private void setLoginStatus(User user, LoginToken loginToken) {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(loginToken.getEmail(), loginToken.getPassword(), new SimpleGrantedAuthority(""));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    private List<GrantedAuthority> getAuthorities(LoginAccount dbAccount) {
//        return dbAccount.getUserAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityType().toString())).collect(Collectors.toList());
//    }
//
//
//    private Authority getAuthority(AuthorityType authorityType) {
//        return authorityRepository.findByAuthorityType(authorityType);
//    }
//
//    private boolean alreadyJoined(LoginToken loginToken) {
//        LoginAccount dbAccount = getByEmailId(loginToken);
//        return dbAccount != null;
//    }


    public List<UserSummaryDto> findByNameLike(String keyword) {
        List<UserSummaryDto> result = new ArrayList<>();
        userRepository.findByNameContaining(keyword).forEach(userInfo -> result.add(new UserSummaryDto(userInfo)));
        return result;
    }

}