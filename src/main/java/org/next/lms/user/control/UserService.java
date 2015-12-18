package org.next.lms.user.control;

import org.next.infra.exception.WrongAccessException;
import org.next.infra.mail.Mail;
import org.next.infra.mail.MailAuth;
import org.next.infra.repository.MailAuthRepository;
import org.next.infra.mail.MailService;
import org.next.infra.mail.template.JoinMailVerifyTemplate;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserDto;
import org.next.lms.user.domain.UserPageDto;
import org.next.lms.user.domain.UserSummaryDto;
import org.next.lms.user.control.inject.LoggedUserInjector;
import org.next.infra.repository.UserRepository;
import org.next.lms.user.domain.AccountState;
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
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.makeUUID;

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
    private MailService mailSender;

    @Autowired
    private Environment environment;

    public Result getSessionUser(User user) {
        if (user == null)
            return new Result(ResponseCode.GetSessionUser.EMPTY);
        return success(new UserDto(user));
    }

    public Result getUser(String id) {
        User user;
        try {
            Long longId = Long.parseLong(id);
            user = userRepository.findOne(longId);
        } catch (NumberFormatException e) {
            user = userRepository.findByEmail(id);
        }
        assureNotNull(user);
        return success(new UserPageDto(user));
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
        if (dbUser != null) {
            if (AccountState.WAIT_FOR_EMAIL_APPROVAL.equals(dbUser.getState())) {
                return new Result(ResponseCode.Login.DO_EMAIL_VERIFY_FIRST);
            }
            return new Result(ResponseCode.Register.ALREADY_EXIST_EMAIL);
        }

        user.encryptPassword(passwordEncoder);
        user.setState(AccountState.WAIT_FOR_EMAIL_APPROVAL);
        userRepository.save(user);

        String uuid = makeUUID();
        mailAuthRepository.save(new MailAuth(uuid, user.getEmail()));
        sendEmailVerifyMail(user, uuid);
        return success();
    }

    private void sendEmailVerifyMail(User user, String uuid) {
        mailSender.sendMail(new Mail(user.getEmail(), new JoinMailVerifyTemplate(environment.getProperty("MAIL_AUTH_API_PATH"), uuid)));

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

    public Result verifyMail(String key) {
        MailAuth mailAuth = mailAuthRepository.findByKey(key);
        if (mailAuth == null) {
            throw new WrongAccessException();
        }

        if (!isFuture(mailAuth.getExpiredTime(), now())) {
            return new Result(ResponseCode.Register.EMAIL_VERIFY_TIMEOUT);
        }

        if (mailAuth.getKey().equals(key)) {
            User user = userRepository.findByEmail(mailAuth.getEmail());
            user.setState(AccountState.ACTIVE);
            return success("메일 인증 성공");
        } else {
            throw new WrongAccessException();
        }
    }

    public boolean isFuture(Date date1, Date date2) {
        int compare = date1.compareTo(date2);
        return compare < 0;
    }

    public Date now() {
        return new Date();
    }
}