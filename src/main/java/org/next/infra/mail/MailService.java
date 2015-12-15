package org.next.infra.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("nextacademicsassistant@gmail.com");
        helper.setTo(mail.getRecipient());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody());

        try {
            mailSender.send(message);
        } catch (MailException ex) {
            throw new RuntimeException();
        }
    }
}
