package org.next.lms.lecture.controller;

import org.next.infra.view.JsonView;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.service.LectureService;
import org.next.lms.user.User;
import org.next.lms.user.inject.Logged;
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
    public JsonView getLecture(Long id, @Logged(makeLoginNeededException = false) User user) {
        if (id == null)
            return successJsonResponse(lectureService.getList());
        return successJsonResponse(lectureService.getById(id, user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonView save(@RequestBody Lecture lecture, @Logged User user) {
        return lectureService.save(lecture, user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView update(@RequestBody Lecture lecture, @Logged User user) {
        return lectureService.update(lecture, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteLecture(Long id, @Logged User user) {
        return lectureService.delete(id, user);
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public JsonView enroll(Long id, @Logged User user) {
        return lectureService.enroll(id, user);
    }

    @RequestMapping(value = "/sideMenu", method = RequestMethod.POST)
    public JsonView sideMenuToggle(Long lectureId, @Logged User user) {
        return lectureService.sideMenuToggle(lectureId, user);
    }

    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public JsonView approval(Long id, Long userId, @Logged User user) {
        return lectureService.approval(id, userId, user);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public JsonView reject(Long id, Long userId, @Logged User user) {
        return lectureService.reject(id, userId, user);
    }

    @RequestMapping(value = "/userGroup", method = RequestMethod.PUT)
    public JsonView userGroup(Long lectureId, Long groupId, Long userId, @Logged User user) {
        return lectureService.userGroupChange(lectureId, groupId, userId, user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/type")
    public JsonView getLectureType(Long id) {
        return successJsonResponse(lectureService.getTypeById(id));
    }
}
