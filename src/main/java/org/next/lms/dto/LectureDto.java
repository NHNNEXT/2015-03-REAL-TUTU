package org.next.lms.dto;

import lombok.Getter;
import org.next.infra.common.domain.MajorType;
import org.next.lms.lecture.domain.Lecture;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LectureDto {
    public LectureDto(Lecture lecture) {
        this.term = new TermDto(lecture.getTerm());
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.managers = new ArrayList<>();
        lecture.getManagers().forEach(referenceTable -> {
            managers.add(new UserSummaryDto(referenceTable.getUserInfo()));
        });
        this.enrolledStudent = new ArrayList<>();
        lecture.getEnrolledStudent().forEach(referenceTable -> {
            enrolledStudent.add(new UserSummaryDto(referenceTable.getUserInfo()));
        });
        contents = new ArrayList<>();
        lecture.getContents().forEach(content -> {
            contents.add(new ContentDto(content));
        });
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.playTime = lecture.getPlayTime();
    }


    private TermDto term;

    private UserSummaryDto hostUser;

    private List<UserSummaryDto> managers;

    private List<UserSummaryDto> enrolledStudent;

    private List<ContentDto> contents;

    private Long id;

    private String name;

    private MajorType majorType;

    private String playTime;
}
