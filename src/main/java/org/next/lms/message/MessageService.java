package org.next.lms.message;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.next.lms.message.repository.MessageRepository;
import org.next.lms.message.template.MessageTemplate;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {


    @Autowired
    MessageRepository messageRepository;

    public static final Integer pageSize = 10;

    public Result getList(User user, Integer page) {
        if (page == null)
            page = 1;
        Pageable pageable = new PageRequest(page, pageSize);
        List<Message> messages = messageRepository.findByUserId(user.getId(), pageable);
        return Result.success(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public Result read(User user, Long id) {
        Message message = messageRepository.findOne(id);
        if (message == null)
            return new Result(ResponseCode.WRONG_ACCESS);
        if (!message.getUser().equals(user))
            return new Result(ResponseCode.WRONG_ACCESS);
        message.setChecked(true);
        messageRepository.save(message);
        return new Result(ResponseCode.SUCCESS);
    }

    public void newMessage(List<User> users, MessageTemplate template) {
        users.forEach(user->{
            Message message = template.getMessage();
            message.setUser(user);
            messageRepository.save(template.getMessage());
        });
    }

    public void newMessage(User user, MessageTemplate template) {
        Message message = template.getMessage();
        message.setUser(user);
        messageRepository.save(message);
    }


}
