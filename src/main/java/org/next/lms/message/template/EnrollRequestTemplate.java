package org.next.lms.message.template;

import org.next.lms.message.Message;

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
