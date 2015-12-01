package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;

@Getter
public class ContentGroupDto {

    private final Long id;
    private final Boolean endTime;
    private final Boolean startTime;
    private final Boolean submit;
    private final Boolean submitOpen;
    private final Boolean reply;
    private final String name;

    public ContentGroupDto(ContentGroup contentGroup) {
        this.id = contentGroup.getId();
        this.endTime = contentGroup.getEndTime();
        this.startTime = contentGroup.getStartTime();
        this.submit = contentGroup.getSubmit();
        this.submitOpen = contentGroup.getSubmitOpen();
        this.reply = contentGroup.getReply();
        this.name = contentGroup.getName();
    }
}
