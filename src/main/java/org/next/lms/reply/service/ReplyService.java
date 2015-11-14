package org.next.lms.reply.service;

import org.next.infra.exception.WrongAccessException;
import org.next.infra.view.JsonView;
import org.next.lms.user.User;
import org.next.lms.reply.auth.ReplyAuth;
import org.next.lms.content.Content;
import org.next.lms.reply.Reply;
import org.next.lms.reply.dto.ReplyDto;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
public class ReplyService {


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyAuth replyAuth;

    public JsonView save(Reply reply, User user, Long contentId) {
        Content content = assureNotNull(contentRepository.findOne(contentId));
        reply.setWriter(user);
        reply.setContent(content);
        reply.setWriteDate(new Date());
        replyRepository.save(reply);
        return successJsonResponse(new ReplyDto(reply));
    }

    public JsonView update(Reply reply, User user) {
        Reply fromDB = assureNotNull(replyRepository.findOne(reply.getId()));
        replyAuth.checkUpdateRight(fromDB, user);
        fromDB.update(reply);
        replyRepository.save(fromDB);
        return successJsonResponse(new ReplyDto(fromDB));
    }

    public JsonView deleteReply(Long id, User user) {
        assureNotNull(id);
        Reply reply = assureNotNull(replyRepository.findOne(id));
        replyAuth.checkDeleteRight(reply, user);
        reply.setDeleteState();
        replyRepository.save(reply);
        return successJsonResponse();
    }

}
