package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.lecture.ContentType;

@Getter
public class ContentTypeDto {

    public ContentTypeDto(ContentType contentType) {
        this.id = contentType.getId();
        this.endTime = contentType.getEndTime();
        this.startTime = contentType.getStartTime();
        this.extendWrite = contentType.getExtendWrite();
        this.onlyWriter = contentType.getOnlyWriter();
        this.statistic = contentType.getStatistic();
        this.name = contentType.getName();
    }


    private Long id;

    private Boolean endTime;

    private Boolean startTime;

    private Boolean extendWrite;

    private Boolean onlyWriter;

    private Boolean statistic;

    private String name;
}
