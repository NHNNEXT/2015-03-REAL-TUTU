package org.next.lms.content.dto;

import lombok.Getter;
import org.next.lms.content.Content;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;

@Getter
public class ContentSummaryDto {

    public ContentSummaryDto(Content content) {
        this.replies = content.getReplies().size();
        this.writer = new UserSummaryDto(content.getWriter());
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.writeDate = content.getWriteDate();
        this.dueDate = content.getDueDate();
        this.type = content.getType();
        this.hits = content.getHits();
        this.body = content.getBody();
        if (this.body.length() > 100)
            this.body = this.body.substring(0, 100);
    }

    private String body;

    private Long hits;

    private Integer replies;

    private UserSummaryDto writer;

    private Integer type;

    private Long lectureId;

    private Long id;

    private String title;

    private Date writeDate;

    private Date dueDate;
}
