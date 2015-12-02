package org.next.lms.content.control;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentDao;
import org.next.lms.content.domain.dto.ContentDto;
import org.next.lms.content.domain.dto.ContentParameterDto;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.content.domain.dto.ContentListDto;
import org.next.lms.content.relative.ContentLinkContent;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.template.NewContentCreatedMessage;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHaveToSubmitRepository userHaveToSubmitRepository;

    @Autowired
    private ContentAuth contentAuthority;

    @Autowired
    private LectureAuth lectureAuthority;

    @Autowired
    private MessageService messageService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ContentLinkContentRepository contentLinkContentRepository;

    public Result getContentDtoById(Long id, User user) {
        Content content = getCheckedContent(id, user);
        content.addReadCount();
        return success(new ContentDto(content, user));
    }

    public Result saveContent(ContentParameterDto contentParameterDto, User user, Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        Content content = contentParameterDto.saveContent(lecture, user, contentRepository, contentGroupRepository, contentAuthority, userRepository, userHaveToSubmitRepository);

        messageService
                // TODO 여기 1 이라는 숫자 바꿔야 함 아직 메시지 그룹핑 정책이 논의된 바 없어서 임의로 1 적음
                .send(new NewContentCreatedMessage(lecture, content, 1))
                .to(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()));
        return success(new ContentDto(content, user));
    }

    public Result update(Content content, User user) {
        Content contentFromDB = assureNotNull(contentRepository.findOne(content.getId()));
        contentAuthority.checkUpdateRight(contentFromDB, user);
        contentFromDB.update(content);
        contentFromDB.validate();
        return success();
    }

    public Result getList(ContentDao contentDao) {
        return success(contentDao.getList(entityManager).stream().map(ContentSummaryDto::new).collect(Collectors.toList()));
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

    public Result saveContents(ContentListDto contents, User user) {
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());
        lectureAuthority.checkUpdateRight(lecture, user);
        contents.getContents().forEach(contentParameterDto -> {
            contentParameterDto.saveContent(lecture, user, contentRepository, contentGroupRepository, contentAuthority, userRepository, userHaveToSubmitRepository);
        });
        return success();
    }

    public Result makeRelation(Long contentId, Long linkContentId, User user) {
        if(contentId.equals(linkContentId))
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
}
