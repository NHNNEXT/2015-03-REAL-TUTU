package org.next.infra.mail.template;

import org.next.infra.mail.MailTemplate;

public class ChangePasswordMail implements MailTemplate {

    private String changePasswordAPIPath;
    private String requestUserMail;
    private String uuid;

    public ChangePasswordMail(String changePasswordAPIPath, String uuid, String requestUserMail) {
        this.changePasswordAPIPath = changePasswordAPIPath;
        this.requestUserMail = requestUserMail;
        this.uuid = uuid;
    }


    @Override
    public String getSubject() {
        return "[NEXT 강의지원] - 비밀번호 변경 메일입니다";
    }

    @Override
    public String getBody() {
        return "<h1>비밀번호 변경 안내</h1>" +
                "<h2><a href=\'" + changePasswordAPIPath + "?key=" + uuid + "&email=" + requestUserMail + "\'>여기</a> 를 눌러 패스워드 변경 페이지로 이동합니다.</h2>" +
                "<strong>" +
                "이 메일은 발송 후 3일간 유효합니다.<br>" +
                "3일 이후에는 비밀번호 변경 요청을 다시 하셔야 합니다" +
                "</strong>";
    }
}
