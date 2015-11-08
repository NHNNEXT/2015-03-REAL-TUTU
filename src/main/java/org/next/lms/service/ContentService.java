package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.auth.LectureAuthority;
import org.next.lms.content.domain.Content;
import org.next.lms.dto.ContentDto;
import org.next.lms.dto.ContentSummaryDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.repository.ContentRepository;
import org.next.lms.repository.LectureRepository;
import org.next.lms.auth.ContentAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ContentAuthority contentAuthority;

    @Autowired
    LectureAuthority lectureAuthority;


    public ContentDto getDtoById(Long id) {
        Content content = assureNotNull(contentRepository.findOne(id));
        content.hits();
        contentRepository.save(content);
        return new ContentDto(content);
    }

    public CommonJsonResponse save(Content content, UserInfo userInfo, Long lectureId) {
        Lecture lecture = lectureRepository.findOne(lectureId);

        if (content.getId() != null)
            return update(content, userInfo, lecture);

        lectureAuthority.checkUpdateRight(lecture, userInfo);

        content.setWriter(userInfo.getLoginAccount());
        content.setWriteDate(new Date());
        content.setLecture(lecture);

        contentRepository.save(content);
        return successJsonResponse(new ContentDto(content));
    }

    private CommonJsonResponse update(Content content, UserInfo userInfo, Lecture lecture) {
        Content fromDB = contentRepository.findOne(content.getId());
        contentAuthority.checkUpdateRight(fromDB, userInfo);
        fromDB.update(content);
        contentRepository.save(fromDB);
        return successJsonResponse();
    }

    public List<ContentSummaryDto> getList() {
        return contentRepository.findAll().stream().map(ContentSummaryDto::new).collect(Collectors.toList());
    }

    public CommonJsonResponse delete(Long id, UserInfo userInfo) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkDeleteRight(content, userInfo);
        content.setDeleteState();
        contentRepository.save(content);
        return successJsonResponse();
    }
}
