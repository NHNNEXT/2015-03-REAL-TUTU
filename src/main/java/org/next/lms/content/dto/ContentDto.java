package org.next.lms.content.dto;

import lombok.Getter;
import org.next.lms.content.Content;
import org.next.lms.reply.dto.ReplyDto;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContentDto {

    public ContentDto(Content content) {
        this.replies = content.getReplies().stream().map(ReplyDto::new).collect(Collectors.toList());
        this.writer = new UserSummaryDto(content.getWriter());
        this.lectureName = content.getLecture().getName();
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.body = content.getBody();
        this.writeDate = content.getWriteDate();
        this.dueDate = content.getDueDate();
        this.type = content.getType();
        this.hits = content.getHits();
        this.likes = content.getLikes().stream().map(like -> like.getId()).collect(Collectors.toList());
        this.types = content.getLecture().getTypes();
    }

    private String types;

    private List<Long> likes;

    private String lectureName;

    private Long hits;

    private List<ReplyDto> replies;

    private UserSummaryDto writer;

    private Integer type;

    private Long lectureId;

    private Long id;

    private String title;

    private String body;

    private Date writeDate;

    private Date dueDate;

    private Long like;
}
