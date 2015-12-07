package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.content.domain.ContentType;

@Getter
public class ContentGroupDto {

    private final Long id;
    private final Boolean submitOpen;
    private final Boolean reply;
    private final Boolean attachment;
    private final String name;
    private final ContentType contentType;

    public ContentGroupDto(ContentGroup contentGroup) {
        this.id = contentGroup.getId();
        this.attachment = contentGroup.getAttachment();
        this.contentType = contentGroup.getContentType();
        this.submitOpen = contentGroup.getSubmitOpen();
        this.reply = contentGroup.getReply();
        this.name = contentGroup.getName();
    }
}
