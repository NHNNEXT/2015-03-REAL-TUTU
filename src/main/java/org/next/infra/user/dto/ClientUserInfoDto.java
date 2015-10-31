package org.next.infra.user.dto;

import lombok.Getter;
import org.next.infra.user.domain.AccountStateType;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;

import static org.next.infra.util.CommonUtils.notNull;

@Getter
public class ClientUserInfoDto {

    private Long userNo;
    private String emailId;
    private AccountStateType accountState;
    private String name;
    private String studentId;
    private String phoneNumber;
    private String major;

    public ClientUserInfoDto(LoginAccount loginAccount) {
        setLoginAccountInfo(loginAccount);
        setUserInfo(loginAccount);
    }

    public void setLoginAccountInfo(LoginAccount loginAccountInfo) {
        this.userNo = loginAccountInfo.getId();
        this.emailId = loginAccountInfo.getEmailId();
        this.accountState = loginAccountInfo.getState();
    }

    public void setUserInfo(LoginAccount loginAccount) {
        UserInfo info = loginAccount.getUserInfo();
        if(notNull(info)) {
            this.name = info.getName();
            this.studentId = info.getStudentId();
            this.phoneNumber = info.getPhoneNumber();
            this.major = info.getMajor();
        }
    }
}