package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
public class LectureDto {


    public LectureDto(Lecture lecture) {
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.managers = lecture.getManagers().stream().map(referenceTable -> new UserSummaryDto(referenceTable.getUserInfo())).collect(toList());
        this.enrolledStudent = lecture.getEnrolledStudent().stream().map(referenceTable -> new UserSummaryDto(referenceTable.getUserInfo())).collect(toList());
        this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(toList());
        this.lessons = lecture.getLessons().stream().map(LessonDto::new).collect(toList());
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.likes = lecture.getLikes().stream().map(like -> like.getUserInfo().getId()).collect(toList());
        this.date = lecture.getDate();
        this.types = lecture.getTypes();
    }

    private String types;
    private Date date;
    private List<Long> likes;
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
