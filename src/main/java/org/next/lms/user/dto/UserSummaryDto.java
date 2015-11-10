package org.next.lms.user.dto;

import lombok.Getter;
import org.next.lms.user.User;

@Getter
public class UserSummaryDto {

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileUrl = user.getProfileUrl();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.phoneNumber = user.getPhoneNumber();
        this.major = user.getMajor();
        this.introduce = user.getIntroduce();
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
