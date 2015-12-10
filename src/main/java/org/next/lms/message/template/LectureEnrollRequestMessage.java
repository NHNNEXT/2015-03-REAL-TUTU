package org.next.lms.message.template;

import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.MultipleEventReportMessageTemplate;
import org.next.lms.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class LectureEnrollRequestMessage extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "'%s' 강의에 '%s' 학생이 가입 승인을 기다리고 있습니다.";
    private static final String multipleEventMessageTemplate = "'%s' 강의에 '%s' 학생 외 '%d' 명이 가입 승인을 기다리고 있습니다.";

    // TODO [수정필요] front - encodeURI 필요
    private static final String urlTemplate = "/lecture/%d?tab=request";

    private Lecture lecture;
    private User user;
    private UserEnrolledLecture relation;

    public LectureEnrollRequestMessage(Lecture lecture, User user, UserEnrolledLecture relation, Integer eventOccurrenceCount) {
        super(eventOccurrenceCount);
        this.lecture = lecture;
        this.user = user;
        this.relation = relation;
    }

    @Override
    protected String singleEventMessage() {
        return String.format(singleEventMessageTemplate, lecture.getName(), user.getName());
    }

    @Override
    protected String multipleEventMessage() {
        List<UserEnrolledLecture> waitForApprovalUsers = relation.getLecture().getUserEnrolledLectures().stream().filter(enrolledUser -> enrolledUser.getApprovalState().equals(ApprovalState.WAITING_APPROVAL)).collect(Collectors.toList());
        return String.format(multipleEventMessageTemplate, lecture.getName(), waitForApprovalUsers.get(0).getUser().getName(), waitForApprovalUsers.size() - 1);
    }

    @Override
    public MessageType messageType() {
        return MessageType.LECTURE_ENROLL_REQUEST;
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
