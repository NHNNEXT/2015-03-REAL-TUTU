package org.next.lms.message.template;

import org.next.infra.util.HanGuelUtil;
import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.message.domain.MessageType;
import org.next.lms.message.structure.MultipleEventReportMessageTemplate;

import static org.next.infra.util.CommonUtils.ellipsis;

public class NewContentCreatedMessage extends MultipleEventReportMessageTemplate {

    private static final String singleEventMessageTemplate = "'%s' 강의에 새로운 게시물 '%s' %s 등록되었습니다.";
    private static final String multipleEventMessageTemplate = "'%s' 강의에 '%d' 개의 새로운 게시물이 있습니다.";

    private static final String singleEventUrlTemplate = "/content/%d";
    private static final String multipleEventUrlTemplate = "/lecture/%d";

    private Lecture lecture;
    private Content content;

    public NewContentCreatedMessage(Lecture lecture, Content content, Integer eventOccurrenceCount) {
        super(eventOccurrenceCount);
        this.lecture = lecture;
        this.content = content;
    }

    @Override
    public String singleEventMessage() {
        String ellipsedTitle = ellipsis(content.getTitle(), 30);
        return String.format(singleEventMessageTemplate, lecture.getName(), ellipsedTitle, HanGuelUtil.josa(ellipsedTitle, HanGuelUtil.JosaType.이가));
    }

    @Override
    public String multipleEventMessage() {
        return String.format(multipleEventMessageTemplate, lecture.getName(), getEventOccurrenceCount());
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
    public String getUrl() {
        if(getEventOccurrenceCount() < 2)
            return String.format(singleEventUrlTemplate, content.getId());
        return String.format(multipleEventUrlTemplate, lecture.getId());
    }
}
