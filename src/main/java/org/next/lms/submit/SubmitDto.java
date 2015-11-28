package org.next.lms.submit;

import lombok.Getter;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SubmitDto {

    private final Long id;
    private final String body;
    private final Date writeDate;
    private final UserSummaryDto writer;

    public SubmitDto(Submit submit) {
        this.id = submit.getId();
        this.body = submit.getBody();
        this.writeDate = submit.getWriteDate();
        this.writer = new UserSummaryDto(submit.getWriter());
    }
}
