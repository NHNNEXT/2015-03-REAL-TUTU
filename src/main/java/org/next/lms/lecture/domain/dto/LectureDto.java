package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.term.TermDto;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public class LectureDto {

    private final UserSummaryDto hostUser;
    private final List<Long> likes;
    private final List<UserGroupDto> userGroups;
    private final List<ContentGroupDto> contentGroups;
    private final List<UserSummaryDto> users;
    private final List<ContentSummaryDto> contents;
    private final Long id;
    private final String name;
    private final Integer majorType;
    private final Integer registerPolicy;
    private TermDto term;
    private List<List<Boolean>> writable;
    private List<List<Boolean>> readable;


    public LectureDto(Lecture lecture, User user) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.registerPolicy = lecture.getRegisterPolicy();
        this.hostUser = new UserSummaryDto(lecture.getHostUser());

        if (user.equals(lecture.getHostUser()))
            this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
        else {
            this.contents = new ArrayList<>();
            try {
                UserGroup userGroup = lecture.getUserGroups().stream()
                        .filter(group -> group.getUserEnrolledLectures().stream()
                                .filter(userEnrolledLecture1 -> userEnrolledLecture1.getUser().equals(user) && ApprovalState.OK.equals(userEnrolledLecture1.getApprovalState())).findAny().isPresent())
                        .findAny().get();
                userGroup.getReadable()
                        .forEach(userGroupCanReadContent -> userGroupCanReadContent.getContentGroup().getContents()
                                .forEach(content -> this.contents.add(new ContentSummaryDto(content))));
            } catch (NoSuchElementException ignored) {
            }
        }
        this.likes = lecture.getUserLikesLectures().stream().map(UserLikesLecture::getId).collect(Collectors.toList());
        this.contentGroups = lecture.getContentGroups().stream().map(ContentGroupDto::new).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream()
                .filter(enrolledUser -> ApprovalState.OK.equals(enrolledUser.getApprovalState()))
                .map(UserSummaryDto::new)
                .collect(Collectors.toList());
        this.userGroups = lecture.getUserGroups().stream().map(UserGroupDto::new).collect(Collectors.toList());
//        if (lecture.getTerm() != null)
//            this.term = new TermDto(lecture.getTerm());
    }
}
