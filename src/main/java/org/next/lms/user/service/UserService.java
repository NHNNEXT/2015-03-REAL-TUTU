package org.next.lms.user.service;

import org.next.infra.result.Result;
import org.next.infra.reponse.ResponseCode;
import org.next.lms.user.*;
import org.next.lms.user.dto.UserDto;
import org.next.lms.user.dto.UserPageDto;
import org.next.lms.user.inject.LoggedUserInjector;
import org.next.lms.user.repository.UserRepository;
import org.next.lms.user.dto.UserSummaryDto;
import org.next.lms.user.state.AccountState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.next.infra.result.Result.success;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public Result getSessionUser(User user) {
        if (user == null)
            return new Result(ResponseCode.GetSessionUser.EMPTY);
        return new Result(ResponseCode.SUCCESS, new UserDto(user));
    }

    public Result getUser(String id) {
        User user;
        try {
            Long longId = Long.parseLong(id);
            user = userRepository.findOne(longId);
        } catch (NumberFormatException e) {
            user = userRepository.findByEmail(id);
        }
        if (user == null)
            return new Result(ResponseCode.WRONG_ACCESS);
        return new Result(ResponseCode.SUCCESS, new UserPageDto(user));
    }


    public Result login(User user, HttpSession session) {
        User dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser == null)
            return new Result(ResponseCode.Login.NOT_EXIST_EMAIL);

        if (AccountState.WITHDRAWAL.equals(dbUser.getState()))
            return new Result(ResponseCode.Login.WITHDRAWAL_ACCOUNT);

        if (!user.isPasswordCorrect(passwordEncoder, dbUser.getPassword())) {
            return new Result(ResponseCode.Login.WRONG_PASSWORD);
        }
        LoggedUserInjector.setUserIdToSession(session, dbUser.getId());
        return new Result(ResponseCode.SUCCESS, new UserDto(dbUser));
    }

    public Result register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new Result(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }
        user.encryptPassword(passwordEncoder);
        userRepository.save(user);

        return new Result(ResponseCode.SUCCESS);
    }

    public Result updateUser(User passed, User dbAccount) {
        if (dbAccount == null)
            return new Result(ResponseCode.GetSessionUser.EMPTY);
        dbAccount.update(passed);
        userRepository.save(dbAccount);
        return success(new UserSummaryDto(dbAccount));
    }

    public Result withdrawal(User user) {
        user.setState(AccountState.WITHDRAWAL);
        return success();
    }


    public Result findByNameLike(String keyword) {
        List<UserSummaryDto> result = new ArrayList<>();
        userRepository.findByNameContaining(keyword).forEach(userInfo -> result.add(new UserSummaryDto(userInfo)));
        return success(result);
    }

}