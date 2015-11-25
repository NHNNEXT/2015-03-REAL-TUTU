package org.next.lms.message.template;

import org.next.lms.message.Message;

public interface MessageTemplate {
    Message getMessage();


    static Message newMessage() {
        return new Message();
    }

    String getUrl();
}
