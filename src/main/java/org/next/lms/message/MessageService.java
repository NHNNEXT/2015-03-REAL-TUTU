package org.next.lms.message;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.lms.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    SessionUtil sessionUtil;

    @Autowired
    MessageRepository messageRepository;

    public static final Integer pageSize = 10;

    public JsonView getList(HttpSession session, Integer page) {
        if (page == null)
            page = 1;
        Pageable pageable = new PageRequest(page, pageSize);
        List<Message> messages = messageRepository.findByUserId(sessionUtil.getLoggedUser(session).getId(), pageable);
        return JsonView.successJsonResponse(messages.stream().map(MessageDto::new).collect(Collectors.toList()));
    }

    public JsonView read(HttpSession session, Long id) {
        Message message = messageRepository.findOne(id);
        if (message == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);
        if (!message.getUser().getId().equals(sessionUtil.getId(session)))
            return new JsonView(ResponseCode.WRONG_ACCESS);
        message.setRead(true);
        messageRepository.save(message);
        return new JsonView(ResponseCode.SUCCESS);
    }
}