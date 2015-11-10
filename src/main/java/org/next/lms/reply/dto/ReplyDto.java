package org.next.lms.reply.dto;

import lombok.Getter;
import org.next.lms.reply.Reply;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReplyDto {
    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.body = reply.getBody();
        this.writeDate = reply.getWriteDate();
        this.writer = new UserSummaryDto(reply.getWriter());
        this.likes = reply.getLikes().stream().map(like -> like.getUser().getId()).collect(Collectors.toList());
    }

    private List<Long> likes;

    private Long id;

    private String body;

    private Date writeDate;

    private UserSummaryDto writer;
}
