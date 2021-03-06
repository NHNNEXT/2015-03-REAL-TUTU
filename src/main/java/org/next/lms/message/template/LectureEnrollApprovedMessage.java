package org.next.lms.message.template;

import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.ImmutableMessageTemplate;

public class LectureEnrollApprovedMessage extends ImmutableMessageTemplate {

    private static final String messageTemplate = "'%s' 강의 개설자가 회원님의 강의 참여 신청을 승인했습니다.";
    private static final String urlTemplate = "/강의/%d";

    private Lecture lecture;

    public LectureEnrollApprovedMessage(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    protected String getMessageString() {
        return String.format(messageTemplate, lecture.getName());
    }

    @Override
    public MessageType messageType() {
        return MessageType.LECTURE_ENROLL_APPROVED;
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
        return String.format(urlTemplate, lecture.getId());
    }
}
