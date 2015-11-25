package org.next.lms.tag.controller;

import org.next.infra.result.Result;
import org.next.lms.tag.dto.TagUpdateDto;
import org.next.lms.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping(method = RequestMethod.GET)
    public Result find(String keyword) {
        return tagService.find(keyword);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/content")
    public Result update(@RequestBody TagUpdateDto tagUpdateDto) {
        return tagService.updateContent(tagUpdateDto);
    }


}
