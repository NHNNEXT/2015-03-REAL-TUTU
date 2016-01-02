package org.next.lms.user.control;

import org.next.infra.exception.WrongAccessException;
import org.next.infra.mail.Mail;
import org.next.infra.mail.MailAuth;
import org.next.infra.mail.MailService;
import org.next.infra.mail.template.ChangePasswordMail;
import org.next.infra.mail.template.JoinMailVerifyTemplate;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.MailAuthRepository;
import org.next.infra.repository.UserRepository;
import org.next.infra.result.Result;
import org.next.infra.util.EnvUtils;
import org.next.lms.user.control.inject.LoggedUserInjector;
import org.next.lms.user.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailAuthRepository mailAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMailService userMailService;

    public Result getSessionUser(User user) {
        if (user == null)
            return new Result(ResponseCode.GetSessionUser.EMPTY);
        return success(new UserDto(user));
    }

    public Result getUser(String email) {
        return success(new UserPageDto(ifNullNotFoundErroReturn(userRepository.findByEmail(email))));
    }

    public Result login(User user, HttpSession session) {
        User dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser == null)
            return new Result(ResponseCode.Login.NOT_EXIST_EMAIL);

        if (AccountState.WITHDRAWAL.equals(dbUser.getState()))
            return new Result(ResponseCode.Login.WITHDRAWAL_ACCOUNT);

        if (!user.isPasswordCorrect(passwordEncoder, dbUser.getPassword()))
            return new Result(ResponseCode.Login.WRONG_PASSWORD);

        if (AccountState.WAIT_FOR_EMAIL_APPROVAL.equals(dbUser.getState())) {
            return new Result(ResponseCode.Login.DO_EMAIL_VERIFY_FIRST);
        }

        LoggedUserInjector.setUserIdToSession(session, dbUser.getId());
        return success(new UserDto(dbUser));
    }

    public Result register(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());

        if (completelyJoinedUser(dbUser)) {
            return new Result(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }

        if (waitingForEmailCertify(dbUser)) {
            return new Result(ResponseCode.Register.ACCOUNT_IS_WAITING_FOR_EMAIL_CERTIFY);
        } else {
            deleteWaitingForCertifyEmailAccount(dbUser);
        }

        user.encryptPassword(passwordEncoder);
        user.setState(AccountState.WAIT_FOR_EMAIL_APPROVAL);
        userRepository.save(user);

        String uuid = makeUUID();
        mailAuthRepository.save(new MailAuth(uuid, user.getEmail(), 1));
        userMailService.sendVerifyMail(user.getEmail(), uuid);
        return success();
    }


    private void deleteWaitingForCertifyEmailAccount(User dbUser) {
        if (dbUser == null) return;

        MailAuth mailAuth = mailAuthRepository.findByEmail(dbUser.getEmail());
        if (!isFuture(now(), mailAuth.getExpiredTime())) {
            mailAuthRepository.delete(mailAuth);
        }
    }

    private boolean waitingForEmailCertify(User dbUser) {
        return dbUser != null && waitingForExpiredTime(dbUser) && waitingForEmailCertifyStatus(dbUser);
    }

    private boolean waitingForEmailCertifyStatus(User dbUser) {
        return AccountState.WAIT_FOR_EMAIL_APPROVAL.equals(dbUser.getState());
    }

    private boolean waitingForExpiredTime(User dbUser) {
        MailAuth mailAuth = mailAuthRepository.findByEmail(dbUser.getEmail());
        return isFuture(now(), mailAuth.getExpiredTime());
    }

    private boolean completelyJoinedUser(User dbUser) {
        return dbUser != null && AccountState.ACTIVE.equals(dbUser.getState());
    }


    public Result updateUser(User passed, User dbAccount) {
        if (dbAccount == null)
            return new Result(ResponseCode.GetSessionUser.EMPTY);
        dbAccount.update(passed);

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