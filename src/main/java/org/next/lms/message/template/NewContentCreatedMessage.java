package org.next.lms.message.template;

import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;

public class NewContentCreatedMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "'%s' 강의에 새로운 게시물이 등록되었습니다.";

    private static final String singleEventUrlTemplate = "/content/%d";

    private Lecture lecture;

    public NewContentCreatedMessage(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public MessageType messageType() {
        return MessageType.NEW_CONTENT_CREATED;
    }

    @Override
    public Long pkAtBelongTypeTable() {
        return lecture.getId();
    }

    @Override
    public Boolean needToExcludeEventEmitUser() {
        return true;
    }

    @Override
    public String getUrl() {
        return String.format(singleEventUrlTemplate, lecture.getId());
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, lecture.getName());
    }
}
