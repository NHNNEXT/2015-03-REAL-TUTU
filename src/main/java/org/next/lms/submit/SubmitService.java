package org.next.lms.submit;

import org.next.config.AppConfig;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.SubmitRepository;
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
public class SubmitService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private SubmitRepository submitRepository;

    @Autowired
    private SubmitAuth submitAuth;

    public Result save(Submit submit, User user, Long contentId) {
        Content content = assureNotNull(contentRepository.findOne(contentId));
        submit.setWriter(user);
        submit.setContent(content);
        submit.setWriteDate(new Date());
        submitRepository.save(submit);
        return success(new SubmitDto(submit));
    }

    public Result update(Submit submit, User user) {
        Submit fromDB = assureNotNull(submitRepository.findOne(submit.getId()));
        submitAuth.checkUpdateRight(fromDB, user);
        fromDB.update(submit);

        return success(new SubmitDto(fromDB));
    }

    public Result deleteReply(Long id, User user) {
        Submit submit = assureNotNull(submitRepository.findOne(id));
        submitAuth.checkDeleteRight(submit, user);
        submit.setDeleteState();
        return success();
    }

    public Result getList(Long contentId, int page) {
        Pageable pageable = new PageRequest(page, AppConfig.pageSize, Sort.Direction.DESC, "writeDate");
        List<Submit> submits = submitRepository.findByContentId(contentId, pageable);
        return success(submits.stream().map(SubmitDto::new).collect(Collectors.toList()));
    }
}
