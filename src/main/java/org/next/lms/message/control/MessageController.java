package org.next.lms.message.control;


import org.next.infra.result.Result;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getList(@Logged User user, @RequestParam(defaultValue = "0") Integer page) {
        return messageService.getList(user, page);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result read(@Logged User user, Long id) {
        return messageService.read(user, id);
    }

}
