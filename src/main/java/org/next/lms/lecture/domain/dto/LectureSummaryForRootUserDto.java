package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureSummaryForRootUserDto extends LectureSummaryDto {

    private boolean sideMenu;
    private List<ContentGroupDto> contentGroups;

    public LectureSummaryForRootUserDto(UserEnrolledLecture userEnrolledLecture) {
        super(userEnrolledLecture.getLecture());
        this.approvalState = userEnrolledLecture.getApprovalState();
        this.sideMenu = userEnrolledLecture.isSideMenu();
        User user = userEnrolledLecture.getUser();
        Lecture lecture = userEnrolledLecture.getLecture();
        if (lecture.isHostUser(user)) {
            this.contentGroups = userEnrolledLecture.getLecture().getContentGroups().stream().map(ContentGroupDto::new).collect(Collectors.toList());
            return;
        }
        this.contentGroups = userEnrolledLecture.getUserGroup().getReadable().stream().map(userGroupCanReadContent-> new ContentGroupDto(userGroupCanReadContent.getContentGroup())).collect(Collectors.toList());
    }

}
