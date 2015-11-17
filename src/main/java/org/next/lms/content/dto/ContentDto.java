package org.next.lms.content.dto;

import lombok.Getter;
import org.next.lms.content.Content;
import org.next.lms.lecture.ContentType;
import org.next.lms.lecture.dto.ContentTypeDto;
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
        this.startTime = content.getStartTime();
        this.endTime = content.getEndTime();
        this.type = new ContentTypeDto(content.getLecture().getContentTypes().get(content.getType()));
        this.hits = content.getHits();
        this.likes = content.getLikes().stream().map(like -> like.getId()).collect(Collectors.toList());
    }
    private List<Long> likes;
    private String lectureName;
    private Long hits;
    private List<ReplyDto> replies;
    private UserSummaryDto writer;
    private ContentTypeDto type;
    private Long lectureId;
    private Long id;
    private String title;
    private String body;
    private Date writeDate;
    private Date startTime;
    private Date endTime;
    private Long like;
}
