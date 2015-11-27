package org.next.lms.message.structure;

import org.next.lms.message.domain.Message;

public interface MessageTemplate {
    Message getMessage();
    String getUrl();
}
