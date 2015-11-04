package org.next.lms.controller;

import org.next.infra.broker.SessionBroker;
import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.dto.ClientUserInfoDto;
import org.next.infra.user.dto.LoginToken;
import org.next.infra.user.service.InfraUserService;
import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.service.ContentService;
import org.next.lms.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private UserInfoBroker userInfoBroker;

    @Autowired
    private ContentService contentService;


    //    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    public CommonJsonResponse getContent(Long id) {
        return successJsonResponse(contentService.getDtoById(id));
    }

    //    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse saveLecture(Content content, Long lectureId, HttpSession session) {
        return contentService.save(content, userInfoBroker.getUserInfo(session), lectureId);
    }
}
