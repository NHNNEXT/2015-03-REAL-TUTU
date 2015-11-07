package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lesson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LessonDto {

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.description = lesson.getDescription();
        this.start = lesson.getStart();
        this.end = lesson.getEnd();
        this.likes = lesson.getLikes().stream().map(like -> like.getUserInfo().getId()).collect(Collectors.toList());
    }

    private List<Long> likes = new ArrayList<>();
    private Long id;
    private String name;
    private String description;
    private Date start;
    private Date end;
}
