package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentGroupDto;
import org.next.lms.lecture.domain.UserEnrolledLecture;

import java.util.ArrayList;
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
        this.contentGroups = new ArrayList<>();
        if (userEnrolledLecture.getUser().equals(userEnrolledLecture.getLecture().getHostUser())) {
            this.contentGroups = userEnrolledLecture.getLecture().getContentGroups().stream()  //[TODO] 로직결정 case1 읽을 수 있는 그룹만 내린다. 2 모두 내린다.
                    .map(ContentGroupDto::new).collect(Collectors.toList());
            return;
        }
        userEnrolledLecture.getUserGroup().getReadable().forEach(userGroupCanReadContent
                -> this.contentGroups.add(new ContentGroupDto(userGroupCanReadContent.getContentGroup())));

    }

}
