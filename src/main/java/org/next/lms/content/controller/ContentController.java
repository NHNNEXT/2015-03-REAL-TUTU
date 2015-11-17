package org.next.lms.content.controller;

import org.next.infra.util.SessionUtil;
import org.next.infra.view.JsonView;
import org.next.lms.content.Content;
import org.next.lms.content.dto.Contents;
import org.next.lms.content.service.ContentService;
import org.next.lms.lecture.controller.LectureController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.next.infra.view.JsonView.successJsonResponse;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private ContentService contentService;


    //    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.GET)
    public JsonView getContent(Long id) {
        return successJsonResponse(contentService.getDtoById(id));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonView getContentList() {
        return successJsonResponse(contentService.getList());
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonView saveContentList(@RequestBody Contents contents, HttpSession session) {
        return contentService.listSave(contents, session);
    }

    //    @Secured({"ROLE_AUTHORIZED", "ROLE_SYSTEM_MANAGER"})
    @RequestMapping(method = RequestMethod.POST)
    public JsonView save(Content content, Long lectureId, HttpSession session) {
        return contentService.save(content, session, lectureId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView updateLecture(Content content, HttpSession session) {
        return contentService.update(content, session);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteLecture(Long id, HttpSession session) {
        return contentService.delete(id, session);
    }
}
