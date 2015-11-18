package org.next.lms.message.template;

import org.next.lms.message.Message;

public class newContentMessageTemplate implements MessageTemplate {



    @Override
    public Message getMessage() {
        return new Message();
    }
}
