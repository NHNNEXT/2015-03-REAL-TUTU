package org.next.lms.controller;

import org.next.infra.broker.SessionBroker;
import org.next.infra.broker.UserInfoBroker;
import org.next.infra.common.dto.CommonJsonResponse;
import org.next.lms.dto.LectureDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.next.infra.common.dto.CommonJsonResponse.successJsonResponse;

@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

//    @RequestMapping(method = RequestMethod.GET)
//    public CommonJsonResponse getAllLectureInfo(Pageable pageable) {
//        // TODO Pageable 적용
//        return successJsonResponse(lecture);
//    }

//    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    public CommonJsonResponse getLectureInfo(Long lectureId) {
        return successJsonResponse(lectureService.getDtoById(lectureId));
    }

//    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public CommonJsonResponse saveLecture(String lessonString, Lecture lecture, String managerIds, HttpSession session) {
        return lectureService.save(lecture, managerIds,lessonString, session);
    }
//
//    @RequestMapping(method = RequestMethod.PUT)
//    public CommonJsonResponse saveLecture(Lecture lecture, Long termId, String managerIds, HttpSession session) {
//        return lectureService.save(lecture, termId, managerIds, userInfoBroker.getUserInfo(session));
//    }
}
