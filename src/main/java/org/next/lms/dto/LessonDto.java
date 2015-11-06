package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.lecture.domain.Lesson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class LessonDto {

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.description = lesson.getDescription();
        this.start = lesson.getStart();
        this.end = lesson.getEnd();
        lesson.getLikes().forEach(like->likes.add(like.getUserInfo().getId()));
    }

    private List<Long> likes = new ArrayList<>();

    private Long id;

    private String name;

    private String description;

    private Date start;

    private Date end;
}
