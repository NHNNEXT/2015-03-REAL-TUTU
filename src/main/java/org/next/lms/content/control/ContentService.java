package org.next.lms.content.control;

import com.mysema.query.jpa.impl.JPAQuery;
import org.next.config.AppConfig;
import org.next.infra.exception.HasNoRightException;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.lms.content.dao.MyListDao;
import org.next.lms.content.domain.Content;
import org.next.lms.content.dao.ContentDao;
import org.next.lms.content.domain.dto.ContentDto;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.content.relative.ContentLinkContent;
import org.next.lms.submit.QUserHaveToSubmit;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.submit.UserHaveToSubmitContentDto;
import org.next.lms.submit.UserHaveToSubmitDto;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentAuth contentAuthority;

    @Autowired
    private UserHaveToSubmitRepository userHaveToSubmitRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ContentLinkContentRepository contentLinkContentRepository;

    @Autowired
    UploadFileRepository uploadFileRepository;

    public Result getContentDtoById(Long id, User user) {
        Content content = getCheckedContent(id, user);
        content.addReadCount();
        return success(new ContentDto(content, user));
    }

    public Result getList(ContentDao contentDao) {
        return success(contentDao.getList(entityManager).stream().map(ContentSummaryDto::new).collect(Collectors.toList()));
    }

    public Result getList(MyListDao myListDao, User user) {
        return success(myListDao.getList(entityManager, user).stream().map(ContentSummaryDto::new).collect(Collectors.toList()));
    }

    public Result getList(User user) {
        List<ContentSummaryDto> contentSummaryDtos = new ArrayList<>();
        user.getEnrolledLectures().forEach(
                userEnrolledLecture -> userEnrolledLecture.getLecture().getContents()
                        .forEach(content -> contentSummaryDtos.add(new ContentSummaryDto(content))));
        return success(contentSummaryDtos);
    }

    public Result delete(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));

        contentAuthority.checkDeleteRight(content, user);

        content.setDeleteState();
        return success();
    }


    public Result makeRelation(Long contentId, Long linkContentId, User user) {
        if (contentId.equals(linkContentId))
            return new Result(ResponseCode.ContentRelation.CANT_BIND_SELF);
        Content linkContent = getCheckedContent(contentId, user);
        Content linkedContent = getCheckedContent(linkContentId, user);
        if (null != contentLinkContentRepository.findByLinkContentIdAndLinkedContentId(contentId, linkContentId))
            return new Result(ResponseCode.ContentRelation.ALREADY_EXIST);
        if (null != contentLinkContentRepository.findByLinkContentIdAndLinkedContentId(linkContentId, contentId))
            return new Result(ResponseCode.ContentRelation.ALREADY_EXIST);
        ContentLinkContent contentLinkContent = new ContentLinkContent();
        contentLinkContent.setLinkContent(linkContent);
        contentLinkContent.setLinkedContent(linkedContent);
        contentLinkContentRepository.save(contentLinkContent);
        return success();
    }

    private Content getCheckedContent(Long contentId, User user) {
        Content content = assureNotNull(contentRepository.findOne(contentId));
        contentAuthority.checkReadRight(content, user);
        return content;
    }

    public Result removeRelation(Long contentId, Long linkContentId, User user) {
        getCheckedContent(contentId, user);
        getCheckedContent(linkContentId, user);
        contentLinkContentRepository.deleteByLinkContentIdAndLinkedContentId(linkContentId, contentId);
        contentLinkContentRepository.deleteByLinkContentIdAndLinkedContentId(contentId, linkContentId);
        return success();
    }


    public Result getHaveToSubmits(Long page, User user) {
        QUserHaveToSubmit qUserHaveToSubmit = QUserHaveToSubmit.userHaveToSubmit;
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qUserHaveToSubmit);
        if(page == null)
            page = 0L;
        query.where(qUserHaveToSubmit.user.eq(user)).limit(AppConfig.pageSize).offset(page * AppConfig.pageSize);
        return success(query.list(qUserHaveToSubmit).stream().map(UserHaveToSubmitContentDto::new).collect(Collectors.toList()));
    }

    public Result updateHaveToSubmits(Long id, Boolean done, User user) {
        UserHaveToSubmit userHaveToSubmit = assureNotNull(userHaveToSubmitRepository.findOne(id));
        if(!user.equals(userHaveToSubmit.getUser()))
            throw new HasNoRightException();
        userHaveToSubmit.setDone(done);
        userHaveToSubmitRepository.save(userHaveToSubmit);
        return success();
    }
}
