package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserGroup;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureForHostUserDto extends LectureDto {

    private final List<UserSummaryDto> waitingUsers;
    private List<List<Boolean>> writable;
    private List<List<Boolean>> readable;
    private List<List<Boolean>> submitReadable;

    public LectureForHostUserDto(Lecture lecture, User hostUser) {
        super(lecture, hostUser);
        this.waitingUsers = lecture.getWaitingUsers().stream().map(UserSummaryDto::new).collect(Collectors.toList());
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
