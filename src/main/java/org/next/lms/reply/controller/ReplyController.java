package org.next.lms.reply.controller;

import org.next.infra.view.JsonView;
import org.next.lms.reply.Reply;
import org.next.lms.reply.service.ReplyService;
import org.next.lms.user.User;
import org.next.lms.user.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.next.infra.view.JsonView.successJsonResponse;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;

    //    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public JsonView save(Reply reply, Long contentId, @Logged User user) {
        return replyService.save(reply, user, contentId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView update(Reply reply, @Logged User user) {
        return replyService.update(reply, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteReply(Long id, @Logged User user) {
        return replyService.deleteReply(id, user);
    }
}
