package org.next.lms.message.domain.template;

import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;

import static org.next.infra.util.CommonUtils.ellipsis;

public class NewContentCreatedMessage extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "'%s' 강의에 새로운 게시물 '%s' 가 등록되었습니다.";
    private static final String multipleEventMessageTemplate = "'%s' 강의에 '%d' 개의 새로운 게시물이 있습니다.";

    private static final String singleEventUrlTemplate = "/content/%d";
    private static final String multipleEventUrlTemplate = "/lecture/%d";

    private Lecture lecture;
    private Content content;

    public NewContentCreatedMessage(Lecture lecture, Content content) {
        this.lecture = lecture;
        this.content = content;
    }

    @Override
    protected String singleEventMessage() {
        return String.format(singleEventMessageTemplate, lecture.getName(), ellipsis(content.getTitle(), 30));
    }

    @Override
    protected String multipleEventMessage() {
        // TODO 몇개인지 알아내는 로직 필요
        return String.format(multipleEventMessageTemplate, lecture.getName(), 1);
    }

    @Override
    protected MessageType messageType() {
        return MessageType.NEW_CONTENT_CREATED;
    }

    @Override
    protected Long pkAtBelongTypeTable() {
        return lecture.getId();
    }

    @Override
    public String getUrl() {
        if(eventOccurrenceCount < 2)
            return String.format(singleEventUrlTemplate, content.getId());
        return String.format(multipleEventUrlTemplate, lecture.getId());
    }
}
