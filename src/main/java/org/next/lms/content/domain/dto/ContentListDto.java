package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentListDto {
    private List<ContentParameterDto> contents;
    private Long lectureId;
}
