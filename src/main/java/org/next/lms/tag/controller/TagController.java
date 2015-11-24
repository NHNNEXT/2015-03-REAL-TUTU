package org.next.lms.tag.controller;

import org.next.infra.view.JsonView;
import org.next.lms.reply.Reply;
import org.next.lms.tag.service.TagService;
import org.next.lms.user.User;
import org.next.lms.user.inject.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping(method = RequestMethod.GET)
    public JsonView find(String keyword) {
        return tagService.find(keyword);
    }


}
