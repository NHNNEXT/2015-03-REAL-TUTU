package org.next.lms.user.domain;

import lombok.Getter;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.domain.dto.LectureSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDto {

    private final Long id;
    private final String email;
    private final String name;
    private String studentId;
    private String phoneNumber;
    private final String major;
    private final String introduce;
    private final String profileUrl;
    private final List<LectureSummaryDto> lectures;
    private final List<LectureSummaryDto> waitingLectures;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profileUrl = user.getProfileUrl();
        this.name = user.getName();
        this.introduce = user.getIntroduce();
        this.major = user.getMajor();
        this.lectures = user.getEnrolledLectures().stream().filter(relation ->
                ApprovalState.OK.equals(relation.getApprovalState())).map(LectureSummaryDto::new).collect(Collectors.toList());
        this.waitingLectures = user.getEnrolledLectures().stream().filter(
                relation -> ApprovalState.WAITING_APPROVAL.equals(relation.getApprovalState())
        ).map(LectureSummaryDto::new).collect(Collectors.toList());
    }

}