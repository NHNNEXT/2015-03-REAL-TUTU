package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LectureTypeDto {

    private Long id;
    private String name;
    private List<ContentTypeDto> contentTypes;

    public LectureTypeDto(Lecture lecture) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.contentTypes = lecture.getContentTypes().stream().map(ContentTypeDto::new).collect(Collectors.toList());
    }

}
