package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.content.ContentType;
import org.next.lms.lecture.UserGroup;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.user.dto.UserSummaryDto;
import org.next.lms.lecture.Lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureDto {


    public LectureDto(Lecture lecture, Boolean host) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.registerPolicy = lecture.getRegisterPolicy();

        this.hostUser = new UserSummaryDto(lecture.getHostUser());

        this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
        this.likes = lecture.getLikes().stream().map(UserLikesLecture::getId).collect(Collectors.toList());
        this.contentTypes = lecture.getContentTypes().stream().map(ContentTypeDto::new).collect(Collectors.toList());
        this.users = new ArrayList<>();
        this.waitingUsers = new ArrayList<>();
        this.rejectUsers = new ArrayList<>();
        lecture.getUsers().forEach(relation -> {
            UserSummaryDto user = new UserSummaryDto(relation.getUser());
            UserGroup userGroup = relation.getUserGroup();
            if (userGroup == null)
                userGroup = lecture.getUserGroups().stream().filter(UserGroup::getDefaultGroup).findFirst().get();
            user.setGroup(new UserGroupDto(userGroup));
            if (ApprovalState.OK.equals(relation.getApprovalState()))
                users.add(user);
            if (ApprovalState.WAITING_APPROVAL.equals(relation.getApprovalState()))
                waitingUsers.add(user);
            if (ApprovalState.REJECT.equals(relation.getApprovalState()))
                rejectUsers.add(user);
        });
        this.userGroups = lecture.getUserGroups().stream().map(UserGroupDto::new).collect(Collectors.toList());

        if (host)
            makeWriteAndRead(lecture);
    }

    private void makeWriteAndRead(Lecture lecture) {
        List<UserGroup> userGroups = lecture.getUserGroups();
        List<ContentType> contentTypes = lecture.getContentTypes();
        this.writable = new ArrayList<>();
        this.readable = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            List<Boolean> writable = new ArrayList<>();
            List<Boolean> readable = new ArrayList<>();
            for (int j = 0; j < contentTypes.size(); j++) {
                final int finalJ = j;
                writable.add(userGroup.getWritable().stream().filter(relation -> relation.getContentType().equals(contentTypes.get(finalJ))).findAny().isPresent());
                readable.add(userGroup.getReadable().stream().filter(relation -> relation.getContentType().equals(contentTypes.get(finalJ))).findAny().isPresent());
            }
            this.writable.add(writable);
            this.readable.add(readable);
        }
    }


    private final UserSummaryDto hostUser;

    private final List<Long> likes;

    private final List<UserGroupDto> userGroups;

    private final List<ContentTypeDto> contentTypes;

    private final List<UserSummaryDto> users;

    private final List<UserSummaryDto> waitingUsers;

    private final List<UserSummaryDto> rejectUsers;

    private final List<ContentSummaryDto> contents;

    private final Long id;

    private final String name;

    private final Integer majorType;

    private final Integer registerPolicy;

    private List<List<Boolean>> writable;

    private List<List<Boolean>> readable;


}
