package org.next.lms.message.template;

import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.MultipleEventReportMessageTemplate;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;

public class UserLikesReplyMessage extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "%s님이 회원님의 댓글을 좋아합니다.";
    private static final String multipleEventMessageTemplate = "%s님 외 %d명이 회원님의 댓글을 좋아합니다.";
    private static final String urlTemplate = "/content/%d";

    private Reply reply;
    private User user;

    public UserLikesReplyMessage(Reply reply, User user, Integer eventOccurrenceCount) {
        super(eventOccurrenceCount);
        this.reply = reply;
        this.user = user;
    }

    @Override
    protected String singleEventMessage() {
        return String.format(singleEventMessageTemplate, user.getName());
    }

    @Override
    protected String multipleEventMessage() {
        return String.format(multipleEventMessageTemplate, user.getName(), getEventOccurrenceCount() - 1);
    }

    @Override
    public MessageType messageType() {
        return MessageType.USER_LIKE_REPLY;
    }

    @Override
    public Long pkAtBelongTypeTable() {
        return reply.getContent().getId();
    }

    @Override
    public String getUrl() {
        return String.format(urlTemplate, reply.getContent().getId());
    }
}
