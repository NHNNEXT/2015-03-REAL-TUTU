package org.next.lms.message.template;

import org.next.lms.content.Content;
import org.next.lms.message.Message;
import org.next.lms.message.MessageType;
import org.next.lms.user.User;

import java.util.Date;

public class UserLikesContentTemplate implements MessageTemplate {

    private static final String single = "%s님이 회원님의 '%s' 게시물을 좋아합니다.";

    private static final String multi = "%s님 외 %d명이 회원님의 '%s' 게시물을 좋아합니다.";

    private static final String url = "/content/%d";

    private Content content;
    private User user;
    private Integer size;

    public UserLikesContentTemplate(Content content, User user, Integer size) {
        this.content = content;
        this.user = user;
        this.size = size;
    }


    public String getMessageString() {
        if (size > 1)
            return String.format(single, user.getName(), content.getTitle());

        return String.format(multi, user.getName(), size - 1, content.getTitle());
    }

    public String getUrl() {
        return String.format(url, content.getId());
    }

    @Override
    public Message getMessage() {
        Message message = new Message();
        message.setType(MessageType.CONTENT);
        message.setTypeId(content.getId());
        message.setMessage(getMessageString());
        message.setUrl(getUrl());
        message.setDate(new Date());
        message.setRead(false);
        return message;
    }
}
