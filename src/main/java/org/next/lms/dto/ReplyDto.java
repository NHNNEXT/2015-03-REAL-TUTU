package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class ReplyDto {
    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.body = reply.getBody();
        this.writeDate = reply.getWriteDate();
        this.writer = new UserSummaryDto(reply.getWriter().getUserInfo());
        reply.getLikes().forEach(like->likes.add(like.getUserInfo().getId()));
    }

    private List<Long> likes = new ArrayList<>();

    private Long id;

    private String body;

    private Date writeDate;

    private UserSummaryDto writer;
}
