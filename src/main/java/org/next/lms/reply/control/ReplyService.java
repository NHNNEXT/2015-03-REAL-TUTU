package org.next.lms.reply.control;

import org.next.config.AppConfig;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.ReplyRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.reply.domain.Reply;
import org.next.lms.reply.domain.ReplyDto;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class ReplyService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyAuth replyAuth;

    public Result save(Reply reply, User user, Long contentId) {
        Content content = assureNotNull(contentRepository.findOne(contentId));
        reply.setWriter(user);
        reply.setContent(content);
        reply.setWriteDate(new Date());

        replyAuth.checkWriteRight(content, user);

        replyRepository.save(reply);
        return success(new ReplyDto(reply));
    }

    public Result update(Reply reply, User user) {
        Reply fromDB = assureNotNull(replyRepository.findOne(reply.getId()));

        replyAuth.checkUpdateRight(fromDB, user);

        fromDB.update(reply);
        return success(new ReplyDto(fromDB));
    }

    public Result deleteReply(Long id, User user) {
        Reply reply = assureNotNull(replyRepository.findOne(id));

        replyAuth.checkDeleteRight(reply, user);

        replyRepository.delete(reply);
        return success();
    }

    public Result getList(Long contentId, int page, User user) {
        Pageable pageable = new PageRequest(page, AppConfig.pageSize, Sort.Direction.DESC, "id");
        List<Reply> replies = replyRepository.findByContentId(contentId, pageable);

        replies.forEach(reply -> replyAuth.checkReadRight(reply, user));

        return success(replies.stream().map(ReplyDto::new).collect(Collectors.toList()));
    }
}
