package org.next.lms.message.domain.template;

import org.next.lms.message.domain.MessageType;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;

public class UserLikesReplyMessageTemplate extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "%s님이 회원님의 댓글을 좋아합니다.";
    private static final String multipleEventMessageTemplate = "%s님 외 %d명이 회원님의 댓글을 좋아합니다.";
    private static final String urlTemplate = "/content/%d";

    private Reply reply;
    private User user;

    public UserLikesReplyMessageTemplate(Reply reply, User user, int eventOccurrenceCount) {
        this.reply = reply;
        this.user = user;
        this.eventOccurrenceCount = eventOccurrenceCount;
    }

    @Override
    protected String singleEventMessage() {
        return String.format(singleEventMessageTemplate, user.getName());
    }

    @Override
    protected String multipleEventMessage() {
        return String.format(multipleEventMessageTemplate, user.getName(), eventOccurrenceCount - 1);
    }

    @Override
    protected MessageType messageType() {
        return MessageType.USER_LIKE_REPLY;
    }

    @Override
    protected Long pkAtBelongTypeTable() {
        return reply.getContent().getId();
    }

    @Override
    public String getUrl() {
        return String.format(urlTemplate, reply.getContent().getId());
    }
}
