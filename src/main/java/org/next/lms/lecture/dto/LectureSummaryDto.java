package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.infra.relation.UserInMenuLecture;
import org.next.lms.lecture.Lecture;

@Getter
public class LectureSummaryDto {

    private final Long id;
    private final String name;
    private final Integer majorType;

    public LectureSummaryDto(Lecture lecture) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
    }
}
