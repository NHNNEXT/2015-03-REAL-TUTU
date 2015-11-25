package org.next.lms.message;

import org.next.infra.result.Result;
import org.next.lms.message.repository.MessageRepository;
import org.next.lms.message.template.MessageTemplate;
import org.next.lms.user.User;
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
        List<Message> messages = messageRepository.findByUserId(user.getId(), pageable);
        return success(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public Result read(User user, Long id) {
        Message message = assureNotNull(messageRepository.findOne(id));
        assureTrue(message.getUser().equals(user));

        message.setChecked(true);
        return success();
    }

    public void newMessage(List<User> users, MessageTemplate template) {
        users.forEach(user -> newMessage(user, template));
    }

    public void newMessage(User user, MessageTemplate template) {
        saveMessage(user, template);
    }

    @Transactional(propagation = Propagation.NESTED)
    private void saveMessage(User user, MessageTemplate template) {
        Message message = template.getMessage();
        message.setUser(user);
        messageRepository.save(message);
    }
}
