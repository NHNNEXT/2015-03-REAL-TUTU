package org.next.lms.message;


import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.next.lms.user.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public JsonView getList(@Logged User user, Integer page) {
        return messageService.getList(user, page);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView read(@Logged User user, Long id) {
        return messageService.read(user, id);
    }

}
