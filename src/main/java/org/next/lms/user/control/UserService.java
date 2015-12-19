package org.next.lms.user.control;

import org.next.infra.exception.WrongAccessException;
import org.next.infra.mail.Mail;
import org.next.infra.mail.MailAuth;
import org.next.infra.mail.template.ChangePasswordMail;
import org.next.infra.repository.MailAuthRepository;
import org.next.infra.mail.MailService;
import org.next.infra.mail.template.JoinMailVerifyTemplate;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.next.infra.util.EnvUtils;
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

    @Autowired
    private EnvUtils envUtils;

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
        mailAuthRepository.save(new MailAuth(uuid, user.getEmail(), 2));
        sendEmailVerifyMail(user, uuid);
        return success();
    }

    private void sendEmailVerifyMail(User user, String uuid) {
        mailSender.sendMail(new Mail(user.getEmail(), new JoinMailVerifyTemplate(envUtils.getAbsoluteURIPath("/api/v1/user/mailVerify"), uuid)));
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

        if (!isFuture(now(), mailAuth.getExpiredTime())) {
            return new Result(ResponseCode.Register.EMAIL_VERIFY_TIMEOUT);
        }

        if (mailAuth.getKey().equals(key)) {
            User user = userRepository.findByEmail(mailAuth.getEmail());
            user.setState(AccountState.ACTIVE);
            mailAuthRepository.delete(mailAuth);
            return success("메일 인증 성공");
        } else {
            throw new WrongAccessException();
        }
    }

    /**
     * 두 날짜를 비교한다
     *
     * @param date1 기준시간
     * @param date2 비교하고 싶은 시간
     * @return 미래인지 아닌지 나타내는 boolean
     */
    public boolean isFuture(Date date1, Date date2) {
        int compare = date1.compareTo(date2);
        return compare < 0;
    }

    public Date now() {
        return new Date();
    }

    public Result sendChangePasswordMail(String email) {
        MailAuth authDuplicateCheck = mailAuthRepository.findByEmail(email);
        if(authDuplicateCheck != null) {
            return new Result(ResponseCode.Login.ALREADY_PASSWORD_CHANGE_MAIL_SENT, "이미 발송된 유효한 비밀번호 변경 메일이 있습니다");
        }

        String key = makeUUID();

        MailAuth mailAuth = new MailAuth(key, email, 1);
        mailAuth.giveTryCount(3);
        mailAuthRepository.save(mailAuth);

        mailSender.sendMail(new Mail(email, new ChangePasswordMail(envUtils.getAbsoluteURIPath("/api/v1/user/changePassword"),key, email)));
        return success();
    }

    @Transactional(noRollbackFor = WrongAccessException.class)
    public Result changePassword(String email, String key, String newPassword) {
        MailAuth mailAuth = mailAuthRepository.findByEmail(email);

        if(mailAuth == null){
            throw new WrongAccessException();
        }

        if(mailAuth.noMoreTryCount()) {
            mailAuthRepository.delete(mailAuth);
            return new Result(ResponseCode.Login.NO_MORE_PASSWORD_CHANGE_TRY_COUNT, "더이상 비밀번호 변경을 시도할 수 없습니다. 비밀번호 변경 요청 메일을 다시 발송하세요");
        }

        if(!mailAuth.getKey().equals(key)) {
            mailAuth.minusTryCount();
            throw new WrongAccessException();
        }

        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        mailAuthRepository.delete(mailAuth);
        return success();
    }
}