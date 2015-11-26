package org.next.lms.message.domain.template;

import org.next.lms.message.domain.Message;

public interface MessageTemplate {
    Message getMessage();


    static Message newMessage() {
        return new Message();
    }

    String getUrl();
}
