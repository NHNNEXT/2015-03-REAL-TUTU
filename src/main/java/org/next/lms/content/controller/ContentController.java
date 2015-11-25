package org.next.lms.content.controller;

import org.next.infra.result.Result;
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


@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private ContentService contentService;


    @RequestMapping(method = RequestMethod.GET)
    public Result getContent(Long id, @Logged(makeLoginNeededException = false) User user) {
        return contentService.getContentDtoById(id, user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getContentList(@Logged User user) {
        return contentService.getList(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result saveContentList(@RequestBody ContentsDto contents, @Logged User user) {
        return contentService.saveContents(contents, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(ContentParameterDto content, Long lectureId, @Logged User user) {
        return contentService.saveContent(content, user, lectureId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateLecture(Content content, @Logged User user) {
        return contentService.update(content, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteLecture(Long id, @Logged User user) {
        return contentService.delete(id, user);
    }
}
