package org.next.lms.message.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class MessageDto {

    private String url;
    private Long id;
    private String message;
    private boolean checked;
    private MessageType type;
    private Date date;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.checked = message.isChecked();
        this.type = message.getType();
        this.date = message.getDate();
        this.url = message.getUrl();
    }
}
