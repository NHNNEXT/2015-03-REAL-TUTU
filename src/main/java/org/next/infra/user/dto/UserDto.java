package org.next.infra.user.dto;

import lombok.Getter;
import org.next.infra.user.domain.AccountStateType;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.dto.LectureDto;
import org.next.lms.dto.Lectures;

import java.util.ArrayList;
import java.util.List;

import static org.next.infra.util.CommonUtils.notNull;

@Getter
public class UserDto {

    private Long id;
    private String email;
    private AccountStateType accountState;
    private String name;
    private String studentId;
    private String phoneNumber;
    private String major;
    private String introduce;
    private String profileUrl;
    private Lectures lectures;

    public UserDto(LoginAccount loginAccount) {
        setLoginAccountInfo(loginAccount);
        UserInfo info = loginAccount.getUserInfo();
        setUserInfo(info);
    }

    public UserDto(UserInfo userInfo) {
        setUserInfo(userInfo);
        LoginAccount account = userInfo.getLoginAccount();
        setLoginAccountInfo(account);
    }

    private void setLoginAccountInfo(LoginAccount loginAccountInfo) {
        this.id = loginAccountInfo.getId();
        this.email = loginAccountInfo.getEmailId();
        this.accountState = loginAccountInfo.getState();
    }

    private void setUserInfo(UserInfo userInfo) {
        this.profileUrl = userInfo.getProfileUrl();
        this.name = userInfo.getName();
        this.studentId = userInfo.getStudentId();
        this.introduce = userInfo.getIntroduce();
        this.phoneNumber = userInfo.getPhoneNumber();
        this.major = userInfo.getMajor();
        this.lectures = new Lectures(userInfo);
    }
}