package org.next.lms.content.control;

import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.dto.ContentDto;
import org.next.lms.content.domain.dto.ContentParameterDto;
import org.next.lms.content.domain.dto.ContentSummaryDto;
import org.next.lms.content.domain.dto.ContentsDto;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.infra.repository.ContentTypeRepository;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.domain.template.newContentMessageTemplate;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ContentTypeRepository contentTypeRepository;

    @Autowired
    private ContentAuth contentAuthority;

    @Autowired
    private LectureAuth lectureAuthority;

    @Autowired
    private MessageService messageService;

    public Result getContentDtoById(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkReadRight(content, user);
        content.addReadCount();
        return success(new ContentDto(content, user));
    }

    public Result saveContent(ContentParameterDto contentParameterDto, User user, Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        Content content = contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        contentAuthority.checkWriteRight(content.getType(), user);

        messageService
                .send(new newContentMessageTemplate(lecture, content))
                .to(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()));
        return success(new ContentDto(content, user));
    }

    public Result update(Content content, User user) {
        content.validateFields();
        Content contentFromDB = assureNotNull(contentRepository.findOne(content.getId()));
        contentAuthority.checkUpdateRight(contentFromDB, user);
        contentFromDB.update(content);
        return success();
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

    public Result saveContents(ContentsDto contents, User user) {
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());
        lectureAuthority.checkUpdateRight(lecture, user);
        contents.getContents().forEach(contentParameterDto -> {
            contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        });
        return success();
    }
}
