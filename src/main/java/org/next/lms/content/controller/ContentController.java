package org.next.lms.content.controller;

import org.next.infra.view.JsonView;
import org.next.lms.content.Content;
import org.next.lms.content.dto.ContentParameterDto;
import org.next.lms.content.dto.ContentsDto;
import org.next.lms.content.service.ContentService;
import org.next.lms.lecture.controller.LectureController;
import org.next.lms.user.User;
import org.next.lms.user.inject.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import static org.next.infra.view.JsonView.successJsonResponse;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private ContentService contentService;


    @RequestMapping(method = RequestMethod.GET)
    public JsonView getContent(Long id, @Logged(makeLoginNeededException = false) User user) {
        return successJsonResponse(contentService.getDtoById(id, user));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonView getContentList() {
        return successJsonResponse(contentService.getList());
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonView saveContentList(@RequestBody ContentsDto contents, @Logged User user) {
        return contentService.listSave(contents, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonView save(ContentParameterDto content, Long lectureId, @Logged User user) {
        return contentService.save(content, user, lectureId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JsonView updateLecture(Content content, @Logged User user) {
        return contentService.update(content, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonView deleteLecture(Long id, @Logged User user) {
        return contentService.delete(id, user);
    }
}
