package org.next.lms.user.dto;

import lombok.Getter;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.lecture.dto.LectureSummaryDto;
import org.next.lms.user.User;

import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean news;
    private List<LectureSummaryDto> lectures;
    private List<LectureSummaryDto> waitingLectures;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profileUrl = user.getProfileUrl();
        this.name = user.getName();
        this.introduce = user.getIntroduce();
        this.major = user.getMajor();
        this.lectures = user.getEnrolledLectures().stream().filter(relation-> ApprovalState.OK.equals(relation.getApprovalState())).map(LectureSummaryDto::new).collect(Collectors.toList());
        this.waitingLectures = user.getEnrolledLectures().stream().filter(relation-> ApprovalState.WAITING_APPROVAL.equals(relation.getApprovalState())).map(LectureSummaryDto::new).collect(Collectors.toList());
        this.news = user.getMessages().stream().filter(
                message -> !message.getChecked()
        ).findAny().isPresent();
    }

}