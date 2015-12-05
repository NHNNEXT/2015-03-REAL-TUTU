package org.next.lms.reply.control;

import org.next.config.AppConfig;
import org.next.infra.result.Result;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;
import org.next.lms.user.control.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;

    @RequestMapping(method = RequestMethod.GET)
    public Result get(Long contentId, int page, @Logged User user) {
        return replyService.getList(contentId, page, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(Reply reply, Long contentId, @Logged User user) {
        return replyService.save(reply, user, contentId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(Reply reply, @Logged User user) {
        return replyService.update(reply, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteReply(Long id, @Logged User user) {
        return replyService.deleteReply(id, user);
    }
}
