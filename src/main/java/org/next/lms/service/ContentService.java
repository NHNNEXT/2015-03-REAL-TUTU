package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.dto.ContentDto;
import org.next.lms.repository.ContentRepository;
import org.next.lms.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    public ContentDto getDtoById(Long id) {
        return new ContentDto(contentRepository.getOne(id));
    }

    public CommonJsonResponse save(Content content, UserInfo userInfo, Long lectureId) {
        content.setWriter(userInfo.getLoginAccount());

        if (lectureId != null)
            content.setLecture(lectureRepository.getOne(lectureId));

        contentRepository.save(content);
        return successJsonResponse(new ContentDto(content));
    }
}
