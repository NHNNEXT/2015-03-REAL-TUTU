package org.next.lms.message;

import lombok.Getter;
import org.next.lms.user.dto.UserSummaryDto;

import java.util.Date;

@Getter
public class MessageDto {

    private Long id;

    private String message;

    private Boolean read;

    private Integer type;

    private Date date;


    public MessageDto(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.read = message.getRead();
        this.type = message.getType();
        this.date = message.getDate();
    }
}
