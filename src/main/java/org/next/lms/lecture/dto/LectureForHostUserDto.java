package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.content.ContentType;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.UserGroup;
import org.next.lms.lecture.auth.ApprovalState;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LectureForHostUserDto extends LectureDto {

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

    private final List<UserSummaryDto> waitingUsers;

    private final List<UserSummaryDto> rejectUsers;

    private List<List<Boolean>> writable;

    private List<List<Boolean>> readable;

}
