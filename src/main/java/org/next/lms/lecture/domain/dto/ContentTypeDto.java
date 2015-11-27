package org.next.lms.lecture.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentType;

@Getter
public class ContentTypeDto {

    private final Long id;
    private final Boolean endTime;
    private final Boolean startTime;
    private final Boolean todo;
    private final Boolean todoOpen;
    private final Boolean reply;
    private final String name;

    public ContentTypeDto(ContentType contentType) {
        this.id = contentType.getId();
        this.endTime = contentType.getEndTime();
        this.startTime = contentType.getStartTime();
        this.todo = contentType.getTodo();
        this.todoOpen = contentType.getTodoOpen();
        this.reply = contentType.getReply();
        this.name = contentType.getName();
    }
}
