package org.next.lms.user.control;

import org.next.infra.exception.PatternNotMatchedException;
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
import org.next.lms.user.domain.AccountState;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.isFuture;
import static org.next.infra.util.CommonUtils.makeUUID;
import static org.next.infra.util.CommonUtils.now;

@Service
@Transactional
public class UserMailService {

    @Autowired
    private MailService mailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private MailAuthRepository mailAuthRepository;

    @Autowired
    private EnvUtils envUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Result verifyMail(String key, String email) {
        User user = userRepository.findByEmail(email);
        if (!AccountState.WAIT_FOR_EMAIL_APPROVAL.equals(user.getState()))
            return new Result(ResponseCode.UserAuth.USER_ALREADY_VERIFIED);

        MailAuth mailAuth = mailAuthRepository.findByEmail(email);
        if (mailAuth == null)
            return new Result(ResponseCode.UserAuth.MAIL_EXPIRED);

        if (!mailAuth.getKey().equals(key))
            return new Result(ResponseCode.UserAuth.KEY_NOT_MATCHED);

        if (!isFuture(now(), mailAuth.getExpiredTime())) {
            mailAuthRepository.delete(mailAuth);
            return new Result(ResponseCode.UserAuth.MAIL_EXPIRED);
        }

        user.setState(AccountState.ACTIVE);
        mailAuthRepository.delete(mailAuth);
        return success("메일 인증 성공");
    }


    public Result resendMailVerify(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new Result(ResponseCode.MailRequest.USER_NOT_EXIST);

        if (!AccountState.WAIT_FOR_EMAIL_APPROVAL.equals(user.getState()))
            return new Result(ResponseCode.MailRequest.USER_ALREADY_VERIFIED);

        MailAuth mailAuth = mailAuthRepository.findByEmail(user.getEmail());
        mailAuth.setExpiredTime(+1);
        sendVerifyMail(user.getEmail(), mailAuth.getKey());
        return success();
    }

    void sendVerifyMail(String email, String key) {
        mailSender.sendMail(new Mail(email, new JoinMailVerifyTemplate(envUtils.getAbsoluteURIPath("/userAuth/userVerify"), key, email)));
    }


    public Result sendChangePasswordMail(String email) {
        User dbUser = userRepository.findByEmail(email);
        if (dbUser == null) {
            return new Result(ResponseCode.MailRequest.USER_NOT_EXIST);
        }

        if (!AccountState.ACTIVE.equals(dbUser.getState())) {
            return new Result(ResponseCode.MailRequest.USER_NOT_VERIFIED);
        }

        MailAuth authDuplicateCheck = mailAuthRepository.findByEmail(email);
        if (authDuplicateCheck != null) {
            return new Result(ResponseCode.MailRequest.ALREADY_PASSWORD_CHANGE_MAIL_SENT, "이미 발송된 유효한 비밀번호 변경 메일이 있습니다");
        }

        String key = makeUUID();

        MailAuth mailAuth = new MailAuth(key, email, 1);
        mailAuth.giveTryCount(3);
        mailAuthRepository.save(mailAuth);
        mailSender.sendMail(new Mail(email, new ChangePasswordMail(envUtils.getAbsoluteURIPath("/userAuth/changePassword"), key, email)));
        return success();
    }


    @Transactional(noRollbackFor = WrongAccessException.class)
    public Result changePassword(String email, String key, String newPassword) {
        MailAuth mailAuth = mailAuthRepository.findByEmail(email);

        if (mailAuth == null) {
            return new Result(ResponseCode.PasswordChange.EXPIRED);
        }

        if (mailAuth.noMoreTryCount()) {
            mailAuthRepository.delete(mailAuth);
            return new Result(ResponseCode.PasswordChange.TOO_MANY_TRY);
        }

        if (!mailAuth.getKey().equals(key)) {
            mailAuth.minusTryCount();
            return new Result(ResponseCode.PasswordChange.KEY_NOT_MATCHED);
        }

        User user = userRepository.findByEmail(email);
        if (newPassword == null || !newPassword.matches(User.PASSWORD_REGEX))
            throw new PatternNotMatchedException("패스워드 형식이 맞지 않습니다.");

        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        mailAuthRepository.delete(mailAuth);
        return success();
    }


}
