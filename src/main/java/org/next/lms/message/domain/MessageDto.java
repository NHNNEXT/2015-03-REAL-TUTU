package org.next.lms.message.domain;

import lombok.Getter;
import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;

import java.util.Date;

@Getter
public class MessageDto {

    private String url;
    private Long id;
    private String message;
    private Boolean read;
    private MessageType type;
    private Date date;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.read = message.getChecked();
        this.type = message.getType();
        this.date = message.getDate();
        this.url = message.getUrl();
    }
}
