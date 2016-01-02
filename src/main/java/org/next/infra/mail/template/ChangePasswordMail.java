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
        // TODO 이메일에서 비밀번호 변경을 바로 할 수 있게 한다면 유효성 검증은 서버에서 해야하니까 에러페이지를 추가로 만들어야 하지 않나?
        return "<h1>비밀번호 변경 안내</h1>"+
                "<h2>아래의 입력창을 통해 변경할 비밀번호를 입력해 주세요</h2>"+

                "<form action='" + changePasswordAPIPath + "' method='post'>"+
                    "<input name='newPassword' type='password' style='border:3px solid #eee; width:500px; height:30px; font-size: 20px; padding: 5px 10px' minlength='6' manlength='20' placeholder='새로운 비밀번호를 입력해 주세요'>"+
                    "<input name='key' type='hidden' value='" + uuid +"'>" +
                    "<input name='email' type='hidden' value='" +requestUserMail + "'>"+
                    "<button style='width:80px; height:46px; padding:5px 10px; border:3px solid #eee; font-size:15px; border-left:0px;background-color:#eee' type='submit'>변경하기</button>"+
                "</form>"+
                "<br><br>"+
                "<strong>"+
                "이 메일은 발송후 3일간 유효합니다.<br>"+
                "3일 이후에는 비밀번호 변경 요청을 다시 하셔야 합니다"+
                "</strong>";
    }
}
