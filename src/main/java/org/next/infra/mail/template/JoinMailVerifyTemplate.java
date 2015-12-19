package org.next.infra.mail.template;

import org.next.infra.mail.MailTemplate;

public class JoinMailVerifyTemplate implements MailTemplate {

    private String mailAuthAPIPath;
    private String uuid;

    public JoinMailVerifyTemplate(String mailAuthAPIPath, String uuid) {
        this.mailAuthAPIPath = mailAuthAPIPath;
        this.uuid = uuid;
    }

    @Override
    public String getSubject() {
        return "[NEXT 강의지원] - 회원가입 인증 메일입니다";
    }

    @Override
    public String getBody() {
        return "<h1>printf(\"Hello World!\");</h1><br>" +
                "<h2><a href=\'" + mailAuthAPIPath + "?key=" + uuid + "\'>여기</a> 를 눌러 입력하신 이메일을 인증하세요!</h2>";
    }
}
