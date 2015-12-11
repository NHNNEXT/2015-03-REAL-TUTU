package org.next.lms.message.structure;

import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;

public interface MessageTemplate {
    Message getMessage();
    String getUrl();

    Boolean isUpdatableMessage();

    MessageType messageType();

    /**
     *  메시지를 발생시킨 객체가 속한 DB Table의 PK
     */
    Long pkAtBelongTypeTable();

    Boolean needToExcludeEventEmitUser();
}
