package org.next.lms.content.control;

import org.next.infra.result.Result;
import org.next.lms.content.dao.ComingScheduleDao;
import org.next.lms.content.dao.ContentDurationDao;
import org.next.lms.content.dao.ContentLimitDao;
import org.next.lms.content.dao.MyListDao;
import org.next.lms.content.domain.dto.ContentListDto;
import org.next.lms.user.control.inject.Logged;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/content/list")
public class ContentListController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentSaveService contentSaveService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getContentList(ContentLimitDao contentLimitDao) {
        return contentService.getList(contentLimitDao);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveContentList(@RequestBody ContentListDto contents, @Logged User user) {
        return contentSaveService.saveContents(contents, user);
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public Result getContentListSize(ContentLimitDao contentLimitDao) {
        return contentService.getListSize(contentLimitDao);
    }

    @RequestMapping(value = "/coming", method = RequestMethod.GET)
    public Result getComingContentList(ComingScheduleDao dao, @Logged User user) {
        return contentService.getList(dao, user);
    }

    @RequestMapping(value = "/duration", method = RequestMethod.GET)
    public Result getContentListByDuration(ContentDurationDao ContentDurationDao) {
        return contentService.getList(ContentDurationDao);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/my")
    public Result searchInMyLectureContents(MyListDao myListDao, @Logged User user) {
        return contentService.getList(myListDao, user);
    }
}
