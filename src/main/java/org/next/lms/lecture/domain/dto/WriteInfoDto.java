package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.dto.ContentTypeDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WriteInfoDto {

    private final Long id;
    private final String name;
    private List<ContentTypeDto> contentTypes;
    private List<UserSummaryDto> users;

    public WriteInfoDto(Lecture lecture) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.contentTypes = lecture.getContentTypes().stream().map(ContentTypeDto::new).collect(Collectors.toList());
        this.users = lecture.getUserEnrolledLectures().stream().map(UserSummaryDto::new).collect(Collectors.toList());
    }

}