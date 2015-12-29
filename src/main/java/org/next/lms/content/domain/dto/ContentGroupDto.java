package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.content.domain.ContentType;

@Getter
public class ContentGroupDto {

    private final Long id;
    private final boolean submitOpen;
    private final boolean reply;
    private final String name;
    private final ContentType contentType;

    public ContentGroupDto(ContentGroup contentGroup) {
        this.id = contentGroup.getId();
        this.contentType = contentGroup.getContentType();
        this.submitOpen = contentGroup.isSubmitOpen();
        this.reply = contentGroup.isReply();
        this.name = contentGroup.getName();
    }
}
