package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.user.domain.UserSummaryDto;

@Getter
public class LectureSummaryDto {

    private Long id;
    private String name;
    private Integer majorType;
    private Boolean sideMenu;
    private Integer approvalState;
    private UserSummaryDto hostUser;

    public LectureSummaryDto(UserEnrolledLecture userEnrolledLecture) {
        Lecture lecture = userEnrolledLecture.getLecture();
        this.approvalState = userEnrolledLecture.getApprovalState();
        this.sideMenu = userEnrolledLecture.getSideMenu();
        setLecture(lecture);
    }

    public LectureSummaryDto(Lecture lecture) {
        setLecture(lecture);
    }

    private void setLecture(Lecture lecture) {
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
    }
}
