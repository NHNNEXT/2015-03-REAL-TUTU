package org.next.lms.content.dto;

import lombok.Getter;
import org.next.lms.content.Content;
import org.next.lms.lecture.dto.ContentTypeDto;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;

@Getter
public class ContentSummaryDto {

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
        if (content.getType() != null)
            this.type = new ContentTypeDto(content.getType());
        this.hits = content.getHits();
        this.body = content.getBody();
        if (this.body != null && this.body.length() > 100)
            this.body = this.body.substring(0, 100);
    }

    private String body;

    private ContentTypeDto type;

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
}
