package org.next.lms.content.domain.dto;

import lombok.Getter;
import org.next.lms.content.domain.Content;
import org.next.lms.tag.domain.Tag;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContentSummaryDto {

    private String body;
    private ContentGroupDto contentGroup;
    private final String lectureName;
    private final Long hits;
    private final Integer repliesSize;
    private final UserSummaryDto writer;
    private final Long lectureId;
    private final Long id;
    private final String title;
    private final Date writeDate;
    private final Date startTime;
    private final Date endTime;
    private final List<String> tags;

    public ContentSummaryDto(Content content) {
        this.repliesSize = content.getReplies().size();
        this.writer = new UserSummaryDto(content.getWriter());
        this.lectureId = content.getLecture().getId();
        this.lectureName = content.getLecture().getName();
        this.id = content.getId();
        this.title = content.getTitle();
        this.writeDate = content.getWriteDate();
        this.startTime = content.getStartTime();
        this.endTime = content.getEndTime();
        if (content.getContentGroup() != null)
            this.contentGroup = new ContentGroupDto(content.getContentGroup());
        this.hits = content.getHits();
        this.body = content.getBody();
        if (this.body != null && this.body.length() > 100)
            this.body = this.body.substring(0, 100);
        this.tags = content.getTags().stream().map(Tag::getText).collect(Collectors.toList());
    }
}
