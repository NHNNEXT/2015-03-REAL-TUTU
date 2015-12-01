package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LectureForHostUserDto extends LectureDto {

    private final List<UserSummaryDto> waitingUsers;
    private final List<UserSummaryDto> rejectUsers;
    private List<List<Boolean>> writable;
    private List<List<Boolean>> readable;
    private List<List<Boolean>> submitReadable;

    public LectureForHostUserDto(Lecture lecture) {
        super(lecture);
        this.waitingUsers = new ArrayList<>();
        this.rejectUsers = new ArrayList<>();
        lecture.getUserEnrolledLectures().forEach(relation -> {
            UserSummaryDto user = new UserSummaryDto(relation.getUser());
            UserGroup userGroup = relation.getUserGroup();
            if (userGroup == null)
                userGroup = lecture.getUserGroups().stream().filter(UserGroup::getDefaultGroup).findFirst().get();
            user.setGroup(new UserGroupDto(userGroup));
            if (ApprovalState.WAITING_APPROVAL.equals(relation.getApprovalState()))
                waitingUsers.add(user);
            if (ApprovalState.REJECT.equals(relation.getApprovalState()))
                rejectUsers.add(user);
        });
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
            for (int j = 0; j < contentGroups.size(); j++) {
                final int finalJ = j;
                writable.add(userGroup.getWritable().stream().filter(relation -> relation.getContentGroup().equals(contentGroups.get(finalJ))).findAny().isPresent());
                readable.add(userGroup.getReadable().stream().filter(relation -> relation.getContentGroup().equals(contentGroups.get(finalJ))).findAny().isPresent());
                submitReadable.add(userGroup.getSubmitReadable().stream().filter(relation -> relation.getContentGroup().equals(contentGroups.get(finalJ))).findAny().isPresent());
            }
            this.writable.add(writable);
            this.readable.add(readable);
            this.submitReadable.add(submitReadable);
        }
    }
}
