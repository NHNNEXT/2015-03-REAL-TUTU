package org.next.lms.reply.controller;

import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.lms.reply.Reply;
import org.next.lms.reply.service.ReplyService;
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
    private SessionUtil sessionUtil;

    @Autowired
    private ReplyService replyService;

//    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public JsonView Reply(Reply reply, Long contentId, HttpSession session) {
        return replyService.saveReply(reply, sessionUtil.getLoggedUser(session), contentId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteReply(Long id, HttpSession session) {
        return replyService.deleteReply(id, sessionUtil.getLoggedUser(session));
    }
}
