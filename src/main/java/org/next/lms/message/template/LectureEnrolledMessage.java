package org.next.lms.message.template;

import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;

public class LectureEnrolledMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "'%s' 강의에 성공적으로 참여하였습니다.";
    private static final String urlTemplate = "/lecture/%d";

    private Lecture lecture;

    public LectureEnrolledMessage(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, lecture.getName());
    }

    @Override
    protected MessageType messageType() {
        return MessageType.LECTURE_ENROLLED;
    }

    @Override
    protected Long pkAtBelongTypeTable() {
        return lecture.getId();
    }

    @Override
    public String getUrl() {
        return String.format(urlTemplate, lecture.getId());
    }
}
