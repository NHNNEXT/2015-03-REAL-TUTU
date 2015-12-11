package org.next.lms.message.domain;

import lombok.Getter;
import org.next.lms.message.structure.MessageTemplate;
import org.next.lms.user.domain.User;

import java.util.List;

@Getter
public class PackagedMessage {
    private User sender;
    private List<User> receiverList;
    private MessageTemplate message;

    public PackagedMessage(User sender, List<User> receiverList, MessageTemplate message) {
        this.sender = sender;
        this.receiverList = receiverList;
        this.message = message;
    }
}
