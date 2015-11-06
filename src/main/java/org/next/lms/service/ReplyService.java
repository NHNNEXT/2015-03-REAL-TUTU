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
        Content content = contentRepository.findOne(contentId);
        if (content == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        reply.setWriter(userInfo.getLoginAccount());
        reply.setContent(content);
        reply.setWriteDate(new Date());
        replyRepository.save(reply);
        return successJsonResponse(new ReplyDto(reply));
    }

    private CommonJsonResponse updateReply(Reply reply, UserInfo userInfo) {
        Reply fromDB = replyRepository.findOne(reply.getId());
        if (!replyAuthority.updateRight(fromDB, userInfo))
            return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);

        if (fromDB == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        fromDB.update(reply);
        replyRepository.save(fromDB);
        return successJsonResponse(new ReplyDto(fromDB));
    }

    public CommonJsonResponse deleteReply(Long id, UserInfo userInfo) {
        if (id == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        Reply reply = replyRepository.findOne(id);
        if (!replyAuthority.deleteRight(reply, userInfo))
            return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);
        replyRepository.delete(reply);
        return successJsonResponse();
    }
}
