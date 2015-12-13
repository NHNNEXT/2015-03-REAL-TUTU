package org.next.lms.lecture.domain.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class LectureNodeChildrenDto {
    private List<LectureSummaryDto> lectures;
    private List<LectureNodeDto> children;

    public LectureNodeChildrenDto(List<LectureSummaryDto> lectures, List<LectureNodeDto> children) {
        this.lectures = lectures;
        this.children = children;
    }
}
