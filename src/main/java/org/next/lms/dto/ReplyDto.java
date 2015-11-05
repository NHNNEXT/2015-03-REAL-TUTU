package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Reply;

import java.util.Date;

@Getter
public class ReplyDto {
    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.body = reply.getBody();
        this.writeDate = reply.getWriteDate();
        this.writer = new UserSummaryDto(reply.getWriter().getUserInfo());

    }

    private Long id;

    private String body;

    private Date writeDate;

    private UserSummaryDto writer;
}
