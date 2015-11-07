package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContentDto {

    public ContentDto(Content content) {
        this.replies = content.getReplies().stream().map(ReplyDto::new).collect(Collectors.toList());
        this.writer = new UserSummaryDto(content.getWriter().getUserInfo());
        this.lectureName = content.getLecture().getName();
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.body = content.getBody();
        this.writeDate = content.getWriteDate();
        this.dueDate = content.getDueDate();
        this.type = content.getType();
        this.hits = content.getHits();
        this.likes = content.getLikes().stream().map(like -> like.getUserInfo().getId()).collect(Collectors.toList());
    }

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
