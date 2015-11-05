package org.next.lms.dto;

import lombok.Getter;
import org.next.infra.user.domain.UserInfo;

@Getter
public class UserSummaryDto {

    public UserSummaryDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.name = userInfo.getName();
        this.profileUrl = userInfo.getProfileUrl();
        this.email = userInfo.getLoginAccount().getEmailId();
        this.studentId = userInfo.getStudentId();
        this.phoneNumber = userInfo.getPhoneNumber();
        this.major = userInfo.getMajor();
        this.introduce = userInfo.getIntroduce();
    }

    private Long id;

    private String email;

    private String name;

    private String profileUrl;

    private String studentId;

    private String phoneNumber;

    private String major;

    private String introduce;

}
