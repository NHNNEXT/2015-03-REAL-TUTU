package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.UserSummaryDto;

@Getter
public class LectureSummaryDto {

    protected Long id;
    protected String name;
    protected Integer majorType;
    protected Integer approvalState;
    protected UserSummaryDto hostUser;

    public LectureSummaryDto(Lecture lecture) {
        setLecture(lecture);
    }

    protected void setLecture(Lecture lecture) {
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
    }
}
