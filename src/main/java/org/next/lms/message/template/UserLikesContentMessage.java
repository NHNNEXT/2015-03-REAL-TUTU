package org.next.lms.message.template;

import org.next.infra.util.CommonUtils;
import org.next.lms.content.domain.Content;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.MultipleEventReportMessageTemplate;
import org.next.lms.user.domain.User;

import static org.next.infra.util.CommonUtils.ellipsis;

public class UserLikesContentMessage extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "%s님이 회원님의 '%s' 게시물을 좋아합니다.";
    private static final String multipleEventMessageTemplate = "%s님 외 %d명이 회원님의 '%s' 게시물을 좋아합니다.";
    private static final String urlTemplate = "/content/%d";

    private Content content;
    private User user;

    public UserLikesContentMessage(Content content, User user, Integer eventOccurrenceCount) {
        super(eventOccurrenceCount);
        this.content = content;
        this.user = user;
    }

    @Override
    protected String singleEventMessage() {
        return String.format(singleEventMessageTemplate, user.getName(), content.getTitle());
    }

    @Override
    protected String multipleEventMessage() {
        return String.format(multipleEventMessageTemplate, user.getName(), getEventOccurrenceCount() - 1, ellipsis(content.getTitle(), 30));
    }

    @Override
    protected MessageType messageType() {
        return MessageType.USER_LIKE_CONTENT;
    }

    @Override
    protected Long pkAtBelongTypeTable() {
        return content.getId();
    }

    @Override
    public String getUrl() {
        return String.format(urlTemplate, content.getId());
    }
}
