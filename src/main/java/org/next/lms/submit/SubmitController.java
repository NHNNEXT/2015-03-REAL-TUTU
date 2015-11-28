package org.next.lms.submit;

import org.next.infra.result.Result;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/submit")
public class SubmitController {


    @Autowired
    private SubmitService submitService;

    @RequestMapping(method = RequestMethod.GET)
    public Result get(Long contentId, int page) {
        return submitService.getList(contentId, page);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(Submit submit, Long contentId, @Logged User user) {
        return submitService.save(submit, user, contentId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(Submit submit, @Logged User user) {
        return submitService.update(submit, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteReply(Long id, @Logged User user) {
        return submitService.deleteReply(id, user);
    }
}
