package org.next.lms.content.dto;

import lombok.Getter;
import org.next.lms.content.Content;
import org.next.lms.lecture.dto.ContentTypeDto;
import org.next.lms.like.UserLikesContent;
import org.next.lms.reply.dto.ReplyDto;
import org.next.lms.user.User;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContentDto {

    private final List<Long> likes;
    private final String lectureName;
    private final Long hits;
    private final List<ReplyDto> replies;
    private final UserSummaryDto writer;
    private final ContentTypeDto type;
    private final Long lectureId;
    private final Long id;
    private final String title;
    private final String body;
    private final Date writeDate;
    private final Date startTime;
    private final Date endTime;
    private Long like;

    public ContentDto(Content content, User user) {
        this.writer = new UserSummaryDto(content.getWriter());
        this.lectureName = content.getLecture().getName();
        this.lectureId = content.getLecture().getId();
        this.id = content.getId();
        this.title = content.getTitle();
        this.body = content.getBody();
        this.writeDate = content.getWriteDate();
        this.startTime = content.getStartTime();
        this.endTime = content.getEndTime();
        this.type = new ContentTypeDto(content.getType());
        this.hits = content.getHits();
        this.likes = content.getUserLikesContents().stream().map(UserLikesContent::getId).collect(Collectors.toList());
        if (!content.getType().getOnlyWriter() || content.getLecture().getHostUser().equals(user)) { // 작성자만 읽기 || Lecture 호스트유저일 경우
            this.replies = content.getReplies().stream().map(ReplyDto::new).collect(Collectors.toList());
            return;
        }
        this.replies = content.getReplies().stream().filter(reply -> reply.getWriter().equals(user)).map(ReplyDto::new).collect(Collectors.toList());
    }
}
