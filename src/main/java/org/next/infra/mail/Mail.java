package org.next.infra.mail;

import lombok.Getter;

@Getter
public class Mail {
    String recipient;
    String subject;
    String body;

    public Mail(String recipient, MailTemplate mailTemplate) {
        this.recipient = recipient;
        this.subject = mailTemplate.getSubject();
        this.body = mailTemplate.getBody();
    }
}
