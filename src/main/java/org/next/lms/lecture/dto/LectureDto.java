package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureDto {

    private final UserSummaryDto hostUser;
    private final List<Long> likes;
    private final List<UserGroupDto> userGroups;
    private final List<ContentTypeDto> contentTypes;
    private final List<UserSummaryDto> users;
    private final List<ContentSummaryDto> contents;
    private final Long id;
    private final String name;
    private final Integer majorType;
    private final Integer registerPolicy;
    private List<List<Boolean>> writable;
    private List<List<Boolean>> readable;

    public LectureDto(Lecture lecture) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.registerPolicy = lecture.getRegisterPolicy();
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
        this.likes = lecture.getUserLikesLectures().stream().map(UserLikesLecture::getId).collect(Collectors.toList());
        this.contentTypes = lecture.getContentTypes().stream().map(ContentTypeDto::new).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream()
                .filter(user -> ApprovalState.OK.equals(user.getApprovalState()))
                .map(UserSummaryDto::new)
                .collect(Collectors.toList());
        this.userGroups = lecture.getUserGroups().stream().map(UserGroupDto::new).collect(Collectors.toList());
    }
}
