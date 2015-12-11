package org.next.lms.message.domain;

import org.next.lms.message.structure.MessageTemplate;
import org.next.lms.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    private User sender;
    private List<User> receiverList = new ArrayList<>();
    private MessageTemplate message;

    private MessageBuilder() {}

    public static MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    public MessageBuilder from(User sender) {
        this.sender = sender;
        return this;
    }

    public MessageBuilder to(User receiver) {
        this.receiverList.add(receiver);
        return this;
    }

    public MessageBuilder to(List<User> receiver) {
        this.receiverList.addAll(receiver);
        return this;
    }

    public MessageBuilder with(MessageTemplate message) {
        this.message = message;
        return this;
    }

    public PackagedMessage packaging() {
        return new PackagedMessage(sender, this.receiverList, message);
    }
}

