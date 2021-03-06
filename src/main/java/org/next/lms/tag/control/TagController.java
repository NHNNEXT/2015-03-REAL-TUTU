package org.next.lms.tag.control;

import org.next.infra.result.Result;
import org.next.lms.tag.domain.TagUpdateDto;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
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
    public Result find(TagDao tagDao) {
        return tagService.find(tagDao);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/content")
    public Result update(@RequestBody TagUpdateDto tagUpdateDto, @Logged User user) {
        return tagService.updateContent(tagUpdateDto, user);
    }


}
