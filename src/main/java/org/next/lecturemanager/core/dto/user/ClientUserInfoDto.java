package org.next.lecturemanager.core.dto.user;

import org.next.lecturemanager.core.domain.user.AccountType;
import org.next.lecturemanager.core.domain.user.LoginAccount;
import org.next.lecturemanager.core.domain.user.AccountStateType;
import org.next.lecturemanager.core.domain.user.UserInfo;

public class ClientUserInfoDto {

    private Long userNo;
    private String emailId;
    private AccountType accountType;
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
        this.accountType = loginAccountInfo.getType();
        this.accountState = loginAccountInfo.getState();
    }

    public void setUserInfo(LoginAccount loginAccount) {
        UserInfo info = loginAccount.getUserInfo();
        this.name = info.getName();
        this.studentId = info.getStudentId();
        this.phoneNumber = info.getPhoneNumber();
        this.major = info.getMajor();
    }

    public Long getUserNo() {
        return userNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public AccountStateType getAccountState() {
        return accountState;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMajor() {
        return major;
    }
}
