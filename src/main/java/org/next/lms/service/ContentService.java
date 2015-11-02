package org.next.lms.service;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.dto.ContentDto;
import org.next.lms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    public ContentDto getDtoById(Long id) {
        return new ContentDto(contentRepository.getOne(id));
    }

    public CommonJsonResponse save(Content content, UserInfo userInfo) {
        content.setWriter(userInfo.getLoginAccount());
        contentRepository.save(content);
        return successJsonResponse();
    }
}
