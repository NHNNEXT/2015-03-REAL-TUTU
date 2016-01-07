package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureForHostUserDto extends LectureDto {

    private final List<UserSummaryDto> waitingUsers;

    public LectureForHostUserDto(Lecture lecture, User hostUser) {
        super(lecture, hostUser);
        this.waitingUsers = lecture.getUserEnrolledLectures().stream().filter(UserEnrolledLecture::approvalStateWaiting).map(UserSummaryDto::new).collect(Collectors.toList());
    }


}
