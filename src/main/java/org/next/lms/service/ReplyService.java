package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.auth.ReplyAuthority;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;
import org.next.lms.dto.ReplyDto;
import org.next.lms.repository.ContentRepository;
import org.next.lms.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
public class ReplyService {


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyAuthority replyAuthority;

    public CommonJsonResponse saveReply(Reply reply, UserInfo userInfo, Long contentId) {
        Long id = reply.getId();
        if (id != null)
            return updateReply(reply, userInfo);
        Content content = assureNotNull(contentRepository.findOne(contentId));
        reply.setWriter(userInfo.getLoginAccount());
        reply.setContent(content);
        reply.setWriteDate(new Date());
        replyRepository.save(reply);
        return successJsonResponse(new ReplyDto(reply));
    }

    private CommonJsonResponse updateReply(Reply reply, UserInfo userInfo) {
        Reply fromDB = assureNotNull(replyRepository.findOne(reply.getId()));
        replyAuthority.checkUpdateRight(fromDB, userInfo);
        fromDB.update(reply);
        replyRepository.save(fromDB);
        return successJsonResponse(new ReplyDto(fromDB));
    }

    public CommonJsonResponse deleteReply(Long id, UserInfo userInfo) {
        assureNotNull(id);
        Reply reply = assureNotNull(replyRepository.findOne(id));
        replyAuthority.checkDeleteRight(reply, userInfo);
        reply.setContent(null);
        reply.setWriteDate(null);
        replyRepository.save(reply);
        return successJsonResponse();
    }
}
