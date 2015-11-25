package org.next.lms.message;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.next.infra.util.CommonUtils;
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

import static org.next.infra.util.CommonUtils.requireNotNull;
import static org.next.infra.util.CommonUtils.requireTrue;

@Service
@Transactional
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    public static final Integer pageSize = 10;

    public Result getList(User user, Integer page) {
        if (page == null)
            page = 1;
        Pageable pageable = new PageRequest(page, pageSize);
        List<Message> messages = messageRepository.findByUserId(user.getId(), pageable);
        return Result.success(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public Result read(User user, Long id) {
        Message message = requireNotNull(messageRepository.findOne(id));
        requireTrue(message.getUser().equals(user));

        message.setChecked(true);
        return new Result(ResponseCode.SUCCESS);
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
