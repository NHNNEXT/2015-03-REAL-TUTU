package org.next.infra.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(Mail mail) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("NextAcademicsAssistant@gmail.com");
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());

            message.setContent(mail.getBody(), "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
