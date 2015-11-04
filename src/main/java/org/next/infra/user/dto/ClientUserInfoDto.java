package org.next.infra.user.dto;

import lombok.Getter;
import org.next.infra.user.domain.AccountStateType;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.dto.LectureDto;

import java.util.ArrayList;
import java.util.List;

import static org.next.infra.util.CommonUtils.notNull;

@Getter
public class ClientUserInfoDto {

    private Long id;
    private String email;
    private AccountStateType accountState;
    private String name;
    private String studentId;
    private String phoneNumber;
    private String major;
    private List<LectureDto> lectures;

    public ClientUserInfoDto(LoginAccount loginAccount) {
        setLoginAccountInfo(loginAccount);
        setUserInfo(loginAccount);
    }

    public void setLoginAccountInfo(LoginAccount loginAccountInfo) {
        this.id = loginAccountInfo.getId();
        this.email = loginAccountInfo.getEmailId();
        this.accountState = loginAccountInfo.getState();
    }

    public void setUserInfo(LoginAccount loginAccount) {
        UserInfo info = loginAccount.getUserInfo();
        if (info == null)
            return;
        this.name = info.getName();
        this.studentId = info.getStudentId();
        this.phoneNumber = info.getPhoneNumber();
        this.major = info.getMajor();
        this.lectures = new ArrayList<>();
        info.getEnrolledLectures().forEach(lecture -> lectures.add(new LectureDto(lecture.getLecture())));
    }
}