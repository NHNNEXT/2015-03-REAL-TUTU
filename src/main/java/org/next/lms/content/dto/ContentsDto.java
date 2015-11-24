package org.next.lms.content.dto;

import lombok.Getter;
import lombok.Setter;
import org.next.lms.content.Content;

import java.util.List;

@Getter
@Setter
public class ContentsDto {
    private List<ContentParameterDto> contents;
    private Long lectureId;
}
