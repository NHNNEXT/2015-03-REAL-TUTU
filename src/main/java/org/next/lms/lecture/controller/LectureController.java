package org.next.lms.lecture.controller;

import org.next.infra.view.JsonView;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.auth.LectureHasAuthInfo;
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
    public JsonView getLectureInfo(Long id) {
        if (id == null)
            return successJsonResponse(lectureService.getList());
        return successJsonResponse(lectureService.getById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonView saveLecture(@RequestBody LectureHasAuthInfo lectureHasAuthInfo, HttpSession session) {
        return lectureService.save(lectureHasAuthInfo, session);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView updateLecture(@RequestBody Lecture lecture, @RequestBody List<List<Boolean>> write, @RequestBody List<List<Boolean>> read, HttpSession session) {
        return lectureService.update(lecture, write, read, session);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteLecture(Long id, HttpSession session) {
        return lectureService.delete(id, session);
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public JsonView enroll(Long id, HttpSession session) {
        return lectureService.enroll(id, session);
    }
}
