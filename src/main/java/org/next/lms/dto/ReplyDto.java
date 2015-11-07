package org.next.lms.dto;

import lombok.Getter;
import org.next.lms.content.domain.Reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReplyDto {
    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.body = reply.getBody();
        this.writeDate = reply.getWriteDate();
        this.writer = new UserSummaryDto(reply.getWriter().getUserInfo());
        this.likes = reply.getLikes().stream().map(like -> like.getUserInfo().getId()).collect(Collectors.toList());
    }

    private List<Long> likes;

    private Long id;

    private String body;

    private Date writeDate;

    private UserSummaryDto writer;
}
