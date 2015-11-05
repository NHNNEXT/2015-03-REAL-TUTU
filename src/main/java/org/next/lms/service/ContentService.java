package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;
import org.next.lms.dto.ContentDto;
import org.next.lms.dto.ContentSummaryDto;
import org.next.lms.dto.ReplyDto;
import org.next.lms.repository.ContentRepository;
import org.next.lms.repository.LectureRepository;
import org.next.lms.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ReplyRepository replyRepository;

    public ContentDto getDtoById(Long id) {
        Content content = contentRepository.getOne(id);
        content.setHits(content.getHits() + 1);
        contentRepository.save(content);
        return new ContentDto(content);
    }

    public CommonJsonResponse save(Content content, UserInfo userInfo, Long lectureId) {
        content.setWriter(userInfo.getLoginAccount());
        content.setWriteDate(new Date());
        if (lectureId != null)
            content.setLecture(lectureRepository.getOne(lectureId));

        contentRepository.save(content);
        return successJsonResponse(new ContentDto(content));
    }

    public List<ContentSummaryDto> getList() {
        List<ContentSummaryDto> dtoList = new ArrayList<>();
        List<Content> list= contentRepository.findAll();
        list.forEach(content->dtoList.add(new ContentSummaryDto(content)));
        return dtoList;
    }

    // [TODO] 컨트롤러 분리
    public CommonJsonResponse saveReply(Reply reply, UserInfo userInfo, Long contentId) {
        Content content = contentRepository.getOne(contentId);
        if(content == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        reply.setWriter(userInfo.getLoginAccount());
        reply.setContent(content);
        reply.setWriteDate(new Date());
        replyRepository.save(reply);
        return successJsonResponse(new ReplyDto(reply));
    }
}
