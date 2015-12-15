package org.next.infra.mail.template;

import org.next.infra.mail.MailTemplate;

public class JoinMailVerifyTemplate implements MailTemplate {
    @Override
    public String getSubject() {
        return "이것이 제목이다";
    }

    @Override
    public String getBody() {
        return "이것이 내용이다";
    }
}
