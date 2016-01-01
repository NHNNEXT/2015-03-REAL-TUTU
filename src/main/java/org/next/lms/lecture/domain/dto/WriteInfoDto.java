package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WriteInfoDto {

    private final Long id;
    private final String name;
    private final Long hostUserId;
    private List<ContentGroupDto> contentGroups;
    private List<UserSummaryDto> users;

    public WriteInfoDto(Lecture lecture, User user) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.hostUserId = lecture.getHostUser().getId();
        if (lecture.isHostUser(user))
            this.contentGroups = lecture.getContentGroups().stream().map(ContentGroupDto::new).collect(Collectors.toList());
        else
            this.contentGroups = lecture.getUserBelongGroup(user).getWritable().stream().map(userGroupCanWriteContent -> new ContentGroupDto(userGroupCanWriteContent.getContentGroup())).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream().filter(userEnrolledLecture -> ApprovalState.OK.equals(userEnrolledLecture.getApprovalState())).map(UserSummaryDto::new).collect(Collectors.toList());
    }

}
