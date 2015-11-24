package org.next.lms.lecture.dto;

import lombok.Getter;
import org.next.lms.content.ContentType;

@Getter
public class ContentTypeDto {

    private final Long id;
    private final Boolean endTime;
    private final Boolean startTime;
    private final Boolean extendWrite;
    private final Boolean onlyWriter;
    private final Boolean statistic;
    private final String name;

    public ContentTypeDto(ContentType contentType) {
        this.id = contentType.getId();
        this.endTime = contentType.getEndTime();
        this.startTime = contentType.getStartTime();
        this.extendWrite = contentType.getExtendWrite();
        this.onlyWriter = contentType.getOnlyWriter();
        this.statistic = contentType.getStatistic();
        this.name = contentType.getName();
    }
}
