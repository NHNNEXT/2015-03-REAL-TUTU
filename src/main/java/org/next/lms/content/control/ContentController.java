package org.next.lms.content.control;

import org.next.infra.result.Result;
import org.next.lms.content.dao.ComingScheduleDao;
import org.next.lms.content.dao.ContentDurationDao;
import org.next.lms.content.dao.ContentLimitDao;
import org.next.lms.content.dao.MyListDao;
import org.next.lms.content.domain.dto.ContentListDto;
import org.next.lms.content.domain.dto.ContentParameterDto;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentSaveService contentSaveService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getContent(Long id, @Logged(makeLoginNeededException = false) User user) {
        return contentService.getContentDtoById(id, user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/submit/require")
    public Result getHaveToSubmits(Long page, @Logged User user) {
        return contentService.getHaveToSubmits(page, user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/require")
    public Result updateHaveToSubmits(Long id, Boolean done, @Logged User user) {
        return contentService.updateHaveToSubmits(id, done, user);
    }

    @RequestMapping(value = "/relative", method = RequestMethod.POST)
    public Result makeRelative(Long contentId, Long linkContentId, @Logged User user) {
        return contentService.makeRelation(contentId, linkContentId, user);
    }

    @RequestMapping(value = "/relative", method = RequestMethod.DELETE)
    public Result removeRelation(Long contentId, Long linkContentId, @Logged User user) {
        return contentService.removeRelation(contentId, linkContentId, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody ContentParameterDto content, @Logged User user) {
        return contentSaveService.save(content, user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody ContentParameterDto content, @Logged User user) {
        return contentSaveService.update(content, user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(Long id, @Logged User user) {
        return contentService.delete(id, user);
    }

    @RequestMapping(value = "/tagBlock", method = RequestMethod.POST)
    public Result updateTagBlock(Long id, boolean tagBlock, @Logged User user) {
        return contentSaveService.updateTagBlock(id,tagBlock, user);
    }

    @RequestMapping(value = "/relativeBlock", method = RequestMethod.POST)
    public Result updateRelativeBlock(Long id, boolean relativeBlock, @Logged User user) {
        return contentSaveService.updateRelativeBlock(id, relativeBlock, user);
    }

}
