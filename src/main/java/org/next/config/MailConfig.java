package org.next.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSenderImpl mailSender() throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getRequiredProperty("SMTP_HOST"));
        mailSender.setPort(Integer.parseInt(environment.getRequiredProperty("SMTP_PORT")));
        mailSender.setUsername(environment.getRequiredProperty("SMTP_USERNAME"));
        mailSender.setPassword(environment.getRequiredProperty("SMTP_PASSWORD"));

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(prop);

        return mailSender;
    }
}