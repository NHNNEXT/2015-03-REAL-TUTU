package org.next.lms.message.template;

import org.next.lms.content.Content;
import org.next.lms.message.Message;
import org.next.lms.message.MessageType;
import org.next.lms.reply.Reply;
import org.next.lms.user.User;

import java.util.Date;

public class UserLikesReplyTemplate implements MessageTemplate {

    private static final String single = "%s님이 회원님의 댓글을 좋아합니다.";

    private static final String multi = "%s님 외 %d명이 회원님의 댓글을 좋아합니다.";

    private static final String url = "/content/%d";

    private Reply reply;
    private User user;
    private Integer size;


    public UserLikesReplyTemplate(Reply reply, User user, int size) {
        this.reply = reply;
        this.user = user;
        this.size = size;
    }

    public String getMessageString() {
        if (size > 3)
            return String.format(single, user.getName());

        return String.format(multi, user.getName(), size - 1);
    }

    public String getUrl() {
        return String.format(url, reply.getContent().getId());
    }

    @Override
    public Message getMessage() {
        Message message = new Message();
        message.setType(MessageType.REPLY);
        message.setTypeId(reply.getContent().getId());
        message.setMessage(getMessageString());
        message.setUrl(getUrl());
        message.setDate(new Date());
        message.setChecked(false);
        return message;
    }
}
