package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class LectureDto {


    public LectureDto(Lecture lecture) {
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
            contents.add(new ContentSummaryDto(content));
        });
        this.lessons = new ArrayList<>();
        lecture.getLessons().forEach(lesson -> {
            lessons.add(new LessonDto(lesson));
        });
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
    }

    private UserSummaryDto hostUser;
    private List<UserSummaryDto> managers;
    private List<UserSummaryDto> enrolledStudent;
    private List<ContentSummaryDto> contents;
    private List<LessonDto> lessons;
    private Long id;
    private String name;
    private Integer majorType;
    private String playTime;
}
