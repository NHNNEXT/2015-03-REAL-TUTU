package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
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
    private List<List<Boolean>> submitReadable;

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
        makeWriteAndRead(lecture);
    }

    private void makeWriteAndRead(Lecture lecture) {
        List<UserGroup> userGroups = lecture.getUserGroups();
        List<ContentGroup> contentGroups = lecture.getContentGroups();
        this.writable = new ArrayList<>();
        this.readable = new ArrayList<>();
        this.submitReadable = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            List<Boolean> writable = new ArrayList<>();
            List<Boolean> readable = new ArrayList<>();
            List<Boolean> submitReadable = new ArrayList<>();

            contentGroups.forEach(contentGroup -> {
                writable.add(userGroup.canWrite(contentGroup));
                readable.add(userGroup.canRead(contentGroup));
                submitReadable.add(userGroup.canSubmitRead(contentGroup));
            });

            this.writable.add(writable);
            this.readable.add(readable);
            this.submitReadable.add(submitReadable);
        }
    }
}
