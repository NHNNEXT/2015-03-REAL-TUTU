package org.next.lms.reply.domain;

import lombok.Getter;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReplyDto {

    private final List<Long> likes;
    private final Long id;
    private final String body;
    private final Date writeDate;
    private final UserSummaryDto writer;

    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.body = reply.getBody();
        this.writeDate = reply.getWriteDate();
        this.writer = new UserSummaryDto(reply.getWriter());
        this.likes = reply.getUserLikesReplies().stream().map(like -> like.getUser().getId()).collect(Collectors.toList());
    }
}
