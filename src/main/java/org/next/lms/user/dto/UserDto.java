package org.next.lms.user.dto;

import lombok.Getter;
import org.next.lms.user.User;
import org.next.lms.lecture.dto.LectureListDto;

@Getter
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String studentId;
    private String phoneNumber;
    private String major;
    private String introduce;
    private String profileUrl;
    private LectureListDto lectures;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profileUrl = user.getProfileUrl();
        this.name = user.getName();
        this.studentId = user.getStudentId();
        this.introduce = user.getIntroduce();
        this.phoneNumber = user.getPhoneNumber();
        this.major = user.getMajor();
        this.lectures = new LectureListDto(user);
    }

}