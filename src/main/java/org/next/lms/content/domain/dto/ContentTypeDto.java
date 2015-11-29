package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentType;

@Getter
public class ContentTypeDto {

    private final Long id;
    private final Boolean endTime;
    private final Boolean startTime;
    private final Boolean submit;
    private final Boolean submitOpen;
    private final Boolean reply;
    private final String name;

    public ContentTypeDto(ContentType contentType) {
        this.id = contentType.getId();
        this.endTime = contentType.getEndTime();
        this.startTime = contentType.getStartTime();
        this.submit = contentType.getSubmit();
        this.submitOpen = contentType.getSubmitOpen();
        this.reply = contentType.getReply();
        this.name = contentType.getName();
    }
}
