package org.next.lms.lecture.control;

import org.next.infra.result.Result;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;
import org.next.lms.user.control.inject.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getLecture(Long id, @Logged(makeLoginNeededException = false) User user) {
        if (id == null)
            return lectureService.getList();
        return lectureService.getLectureById(id, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Lecture lecture, @Logged User user) {
        return lectureService.save(lecture, user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody Lecture lecture, @Logged User user) {
        return lectureService.update(lecture, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteLecture(Long id, @Logged User user) {
        return lectureService.delete(id, user);
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public Result enroll(Long id, @Logged User user) {
        return lectureService.enroll(id, user);
    }

    @RequestMapping(value = "/sideMenu", method = RequestMethod.POST)
    public Result sideMenuToggle(Long lectureId, @Logged User user) {
        return lectureService.sideMenuToggle(lectureId, user);
    }

    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public Result approval(Long id, Long userId, @Logged User user) {
        return lectureService.approval(id, userId, user);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public Result reject(Long id, Long userId, @Logged User user) {
        return lectureService.reject(id, userId, user);
    }

    @RequestMapping(value = "/userGroup", method = RequestMethod.PUT)
    public Result userGroup(Long lectureId, Long groupId, Long userId, @Logged User user) {
        return lectureService.userGroupChange(lectureId, groupId, userId, user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/type")
    public Result getLectureType(Long id) {
        return lectureService.getSupportContentTypeById(id);
    }
}
