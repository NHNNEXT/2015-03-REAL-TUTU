package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.user.dto.UserSummaryDto;
import org.next.lms.lecture.Lecture;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class LectureDto {

    public LectureDto(Lecture lecture) {
        this.hostUser = new UserSummaryDto(lecture.getHostUser());
        this.users = lecture.getUserEnrolledLectures().stream().map(referenceTable -> new UserSummaryDto(referenceTable.getUser())).collect(toList());
        this.contents = lecture.getContents().stream().map(ContentSummaryDto::new).collect(toList());
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.majorType = lecture.getMajorType();
        this.likes = lecture.getLikes().stream().map(like -> like.getUser().getId()).collect(toList());
    }

    private String types;
    private Date date;
    private List<Long> likes;
    private UserSummaryDto hostUser;
    private List<UserSummaryDto> managers;
    private List<UserSummaryDto> users;
    private List<ContentSummaryDto> contents;
    private Long id;
    private String name;
    private Integer majorType;
    private String playTime;

}
