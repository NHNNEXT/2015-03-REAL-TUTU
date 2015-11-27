package org.next.lms.message.structure;

import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;

public abstract class MultipleEventReportMessageTemplate extends MutableMessageTemplate {

    protected Integer eventOccurrenceCount;

    @Override
    public Message getMessage() {
        Message message = new Message();
        message.setType(messageType());
        message.setPkAtBelongTypeTable(pkAtBelongTypeTable());
        message.setMessage(getMessageString());
        message.setUrl(getUrl());
        return message;
    }

    private String getMessageString() {
        if (eventOccurrenceCount < 2)
            return singleEventMessage();
        return multipleEventMessage();
    }

    protected abstract String singleEventMessage();
    protected abstract String multipleEventMessage();

    protected abstract MessageType messageType();

    /**
     *  메시지를 발생시킨 객체가 속한 DB Table의 PK
     */
    protected abstract Long pkAtBelongTypeTable();
}