package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.next.lms.lecture.domain.LectureNode;


@Getter
@Setter
public class LectureNodeDto {
    private Long id;
    private String name;

    public LectureNodeDto(LectureNode lectureNode) {
        this.id = lectureNode.getId();
        this.name = lectureNode.getName();
    }
}
