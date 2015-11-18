package org.next.lms.lecture.controller;

import org.next.infra.view.JsonView;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import java.util.ArrayList;
import java.util.List;

import static org.next.infra.view.JsonView.successJsonResponse;

@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    @RequestMapping(method = RequestMethod.GET)
    public JsonView getLectureInfo(Long id, HttpSession session) {
        if (id == null)
            return successJsonResponse(lectureService.getList());
        return successJsonResponse(lectureService.getById(id, session));
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonView save(@RequestBody Lecture lecture, HttpSession session) {
        return lectureService.save(lecture, session);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView update(@RequestBody Lecture lecture, HttpSession session) {
        return lectureService.update(lecture, session);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteLecture(Long id, HttpSession session) {
        return lectureService.delete(id, session);
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public JsonView enroll(Long id, HttpSession session) {
        return lectureService.enroll(id, session);
    }

    @RequestMapping(value = "/sideMenu", method = RequestMethod.POST)
    public JsonView sideMenuToggle(Long lectureId, HttpSession session) {
        return lectureService.sideMenuToggle(lectureId, session);
    }

    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public JsonView approval(Long id, Long userId, HttpSession session) {
        return lectureService.approval(id, userId, session);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public JsonView reject(Long id, Long userId, HttpSession session) {
        return lectureService.reject(id, userId, session);
    }

    @RequestMapping(value = "/userGroup", method = RequestMethod.PUT)
    public JsonView userGroup(Long lectureId, Long groupId, Long userId, HttpSession session) {
        return lectureService.userGroupChange(lectureId, groupId, userId,  session);
    }
}
