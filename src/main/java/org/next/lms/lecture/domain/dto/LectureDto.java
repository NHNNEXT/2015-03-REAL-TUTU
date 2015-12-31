package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureDto {

    private final UserSummaryDto hostUser;
    private final List<Long> likes;
    private final List<UserGroupDto> userGroups;
    private final List<ContentGroupDto> contentGroups;
    private final List<UserSummaryDto> users;
    private final Long id;
    private final String name;
    private final Integer majorType;
    private final Integer registerPolicy;
    private final Integer contentLength;
    private final long scheduleLength;
    private List<List<Boolean>> writable;
    private List<List<Boolean>> readable;

    public LectureDto(Lecture lecture, User user) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.registerPolicy = lecture.getRegisterPolicy();
        this.contentLength = lecture.getContents().size();
        this.scheduleLength = lecture.getContents().stream().filter(content -> content.isSchedule() || content.isSubmit()).count();
        this.hostUser = new UserSummaryDto(lecture.getHostUser());

        this.likes = lecture.getUserLikesLectures().stream().map(UserLikesLecture::getId).collect(Collectors.toList());
        this.contentGroups = lecture.getContentGroups().stream().map(ContentGroupDto::new).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream().filter(UserEnrolledLecture::approvalStateOk).map(UserSummaryDto::new).collect(Collectors.toList());
        this.userGroups = lecture.getUserGroups().stream().map(UserGroupDto::new).collect(Collectors.toList());
    }
}
