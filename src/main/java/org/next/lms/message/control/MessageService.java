package org.next.lms.message.control;

import org.next.infra.result.Result;
import org.next.infra.repository.MessageRepository;
import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageDto;
import org.next.lms.message.structure.MessageTemplate;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.assureTrue;

@Service
@Transactional
public class MessageService {

    public static final Integer pageSize = 10;

    @Autowired
    private MessageRepository messageRepository;

    public Result getList(User user, Integer page) {
        Pageable pageable = new PageRequest(page, pageSize);
        List<Message> messages = messageRepository.findByReceiverId(user.getId(), pageable);
        return success(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public Result read(User user, Long id) {
        Message message = assureNotNull(messageRepository.findOne(id));
        assureTrue(message.getReceiver().equals(user));

        message.read();
        return success();
    }

    public MessageHolder send(MessageTemplate message) {
        return new MessageHolder(message);
    }

    public class MessageHolder {
        private MessageTemplate message;

        private MessageHolder(MessageTemplate message) {
            this.message = message;
        }

        public void to(User receiver) {
            sendMessageNow(receiver, message);
        }

        public void to(List<User> receivers) {
            sendMessageNow(receivers, message);
        }
    }

    private void sendMessageNow(List<User> receivers, MessageTemplate template) {
        receivers.forEach(user -> sendMessageNow(user, template));
    }

    private void sendMessageNow(User receiver, MessageTemplate template) {
        saveMessage(receiver, template);
    }

    private void saveMessage(User receiver, MessageTemplate template) {
        Message message = template.getMessage();
        message.setReceiver(receiver);
        messageRepository.save(message);
    }
}
