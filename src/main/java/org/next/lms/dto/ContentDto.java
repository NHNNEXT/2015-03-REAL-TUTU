package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class ContentDto {
    public ContentDto(Content content) {
        this.replies = new ArrayList<>();
        content.getReplies().forEach(reply -> replies.add(reply));
        this.writer = new UserSummaryDto(content.getWriter().getUserInfo());
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.body = content.getBody();
        this.writeDate = content.getWriteDate();
        this.dueDate = content.getDueDate();
    }

    private List<Reply> replies;

    private UserSummaryDto writer;

    private Long lectureId;

    private Long id;

    private String title;

    private String body;

    private Date writeDate;

    private Date dueDate;

    private Long like;
}
