package org.next.lms.message.control;

import lombok.Setter;
import org.next.lms.message.structure.MessageTemplate;
import org.next.lms.user.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Setter
@Scope("prototype")
@Component
@Transactional
public class MessageHolder {

    private MessageTemplate message;
    private MessageService messageService;

    public void to(User receiver) {
        messageService.sendMessageNow(receiver, message);
    }

    public void to(List<User> receivers) {
        messageService.sendMessageNow(receivers, message);
    }
}
