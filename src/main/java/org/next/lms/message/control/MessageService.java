package org.next.lms.message.control;

import org.next.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.infra.util.CommonUtils.assureTrue;

@Slf4j
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageHolder messageHolder;

    public Result getList(User user, Integer page) {
        Pageable pageable = new PageRequest(page, AppConfig.pageSize);
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
        messageHolder.setMessage(message);
        messageHolder.setMessageService(this);
        return messageHolder;
    }

    public void sendMessageNow(List<User> receivers, MessageTemplate template) {
        receivers.forEach(user -> sendMessageNow(user, template));
    }

    public void sendMessageNow(User receiver, MessageTemplate template) {
        saveMessage(receiver, template);
    }

    private void saveMessage(User receiver, MessageTemplate messageHolder) {
        Message messageFromDb = messageRepository.findByReceiverIdAndTypeAndPkAtBelongTypeTable(receiver.getId(), messageHolder.messageType(), messageHolder.pkAtBelongTypeTable());

        if(messageFromDb == null) {
            createNewMessage(receiver, messageHolder);
            return;
        }

        if(messageHolder.isUpdatableMessage()) {
            updateMessage(messageFromDb, messageHolder);
        }
    }

    private void updateMessage(Message messageFromDb, MessageTemplate messageHolder) {
        messageFromDb.setMessage(messageHolder.getMessage().getMessage());
        messageFromDb.setDate(new Date());
    }

    private void createNewMessage(User receiver, MessageTemplate messageHolder) {
        Message message = messageHolder.getMessage();
        message.setReceiver(receiver);
        messageRepository.save(message);
    }
}
