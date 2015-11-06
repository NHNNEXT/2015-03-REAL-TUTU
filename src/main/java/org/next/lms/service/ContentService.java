package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.auth.LectureAuthority;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.Reply;
import org.next.lms.dto.ContentDto;
import org.next.lms.dto.ContentSummaryDto;
import org.next.lms.dto.ReplyDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.repository.ContentRepository;
import org.next.lms.repository.LectureRepository;
import org.next.lms.repository.ReplyRepository;
import org.next.lms.auth.ContentAuthority;
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

    @Autowired
    ContentAuthority contentAuthority;

    @Autowired
    LectureAuthority lectureAuthority;

    public ContentDto getDtoById(Long id) {
        Content content = contentRepository.getOne(id);
        content.setHits(content.getHits() + 1);
        contentRepository.save(content);
        return new ContentDto(content);
    }

    public CommonJsonResponse save(Content content, UserInfo userInfo, Long lectureId) {
        if (lectureId == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);

        Lecture lecture = lectureRepository.getOne(lectureId);
        if (lecture == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);

        if (!lectureAuthority.hasAuthority(lecture, userInfo))
            return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);

        if (content.getId() != null)
            return update(content, userInfo, lecture);

        content.setWriter(userInfo.getLoginAccount());
        content.setWriteDate(new Date());
        content.setLecture(lecture);

        contentRepository.save(content);
        return successJsonResponse(new ContentDto(content));
    }

    private CommonJsonResponse update(Content content, UserInfo userInfo, Lecture lecture) {
        Content fromDB = contentRepository.getOne(content.getId());
        if (!contentAuthority.hasUpdateRight(fromDB, userInfo))
            return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);
        fromDB.update(content);
        contentRepository.save(fromDB);
        return successJsonResponse();
    }

    public List<ContentSummaryDto> getList() {
        List<ContentSummaryDto> dtoList = new ArrayList<>();
        List<Content> list = contentRepository.findAll();
        list.forEach(content -> dtoList.add(new ContentSummaryDto(content)));
        return dtoList;
    }

    // [TODO] 컨트롤러 분리
    public CommonJsonResponse saveReply(Reply reply, UserInfo userInfo, Long contentId) {
        Content content = contentRepository.getOne(contentId);
        if (content == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        reply.setWriter(userInfo.getLoginAccount());
        reply.setContent(content);
        reply.setWriteDate(new Date());
        replyRepository.save(reply);
        return successJsonResponse(new ReplyDto(reply));
    }

    public CommonJsonResponse delete(Long id, UserInfo userInfo) {
        Content content = contentRepository.getOne(id);
        if (content == null)
            return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
        if (!contentAuthority.hasDeleteRight(content, userInfo))
            return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);
        contentRepository.delete(id);
        return successJsonResponse();
    }
}
