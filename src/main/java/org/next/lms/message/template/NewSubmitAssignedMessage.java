package org.next.lms.message.template;

import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;

public class NewSubmitAssignedMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "'%s' 과목에 새로운 과제가 있습니다.";

    private static final String singleEventUrlTemplate = "/강의/%d";

    private Lecture lecture;

    public NewSubmitAssignedMessage(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, lecture.getName());
    }

    @Override
    public MessageType messageType() {
        return MessageType.NEW_SUBMIT_ASSIGNED;
    }

    @Override
    public Long pkAtBelongTypeTable() {
        return lecture.getId();
    }

    @Override
    public Boolean needToExcludeEventEmitUser() {
        return false;
    }

    @Override
    public String getUrl() {
        return String.format(singleEventUrlTemplate, lecture.getId());
    }
}
