package org.next.lms.controller;

import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;
import org.next.lms.service.ContentService;
import org.next.lms.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private ReplyService replyService;

//    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse Reply(Reply reply, Long contentId, HttpSession session) {
        return replyService.saveReply(reply, userInfoBroker.getUserInfo(session), contentId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public CommonJsonResponse deleteReply(Long id, HttpSession session) {
        return replyService.deleteReply(id, userInfoBroker.getUserInfo(session));
    }
}
