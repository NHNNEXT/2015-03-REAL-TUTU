package org.next.lms.message.domain.template;

import org.next.lms.message.domain.Message;

public class EnrollRequestTemplate implements MessageTemplate {



    @Override
    public Message getMessage() {
        return new Message();
    }

    @Override
    public String getUrl() {
        return null;
    }
}
