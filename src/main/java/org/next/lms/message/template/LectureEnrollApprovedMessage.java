package org.next.lms.message.template;

import org.next.lms.message.domain.Message;
import org.next.lms.message.structure.MessageTemplate;

public class LectureEnrollApprovedMessage implements MessageTemplate {

    @Override
    public Message getMessage() {
        return new Message();
    }

    @Override
    public String getUrl() {
        return null;
    }
}
