package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Content;

import java.util.Date;

@Getter
public class ContentSummaryDto {

    public ContentSummaryDto(Content content) {
        this.replies = content.getReplies().size();
        this.writer = new UserSummaryDto(content.getWriter().getUserInfo());
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.writeDate = content.getWriteDate();
        this.dueDate = content.getDueDate();
    }

    private Integer replies;

    private UserSummaryDto writer;

    private Long lectureId;

    private Long id;

    private String title;

    private Date writeDate;

    private Date dueDate;
}
