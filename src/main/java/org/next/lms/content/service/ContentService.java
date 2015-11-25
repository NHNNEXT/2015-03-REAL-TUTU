package org.next.lms.content.service;

import org.next.infra.reponse.ResponseCode;
import org.next.infra.view.JsonView;
import org.next.lms.content.dto.ContentParameterDto;
import org.next.lms.content.dto.ContentsDto;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.message.MessageService;
import org.next.lms.message.template.newContentMessageTemplate;
import org.next.lms.user.User;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.lms.content.Content;
import org.next.lms.content.dto.ContentDto;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.lecture.Lecture;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.lms.content.auth.ContentAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.view.JsonView.successJsonResponse;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
public class ContentService {


    @Autowired
    ContentRepository contentRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ContentAuth contentAuthority;

    @Autowired
    LectureAuth lectureAuthority;

    @Autowired
    MessageService messageService;

    @Autowired
    ContentTypeRepository contentTypeRepository;

    public ContentDto getDtoById(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkReadRight(content, user);
        content.hits();
        contentRepository.save(content);
        return new ContentDto(content, user);
    }

    public JsonView save(ContentParameterDto contentParameterDto, User user, Long lectureId) {
        if (lectureId == null)
            return new JsonView(ResponseCode.WRONG_ACCESS);
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        Content content = contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        messageService.newMessage(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new newContentMessageTemplate());
        return successJsonResponse(new ContentDto(content, user));
    }

    public JsonView update(Content content, User user) {
        Content fromDB = contentRepository.findOne(content.getId());
        fromDB.update(content);
        contentAuthority.checkUpdateRight(fromDB, user);
        content.fieldCheck();
        contentRepository.save(fromDB);
        return successJsonResponse();
    }

    public List<ContentSummaryDto> getList(User user) {
        List<ContentSummaryDto> contentSummaryDtos = new ArrayList<>();
        user.getEnrolledLectures().forEach(
                userEnrolledLecture -> userEnrolledLecture.getUserGroup().getReadable()
                        .forEach(userGroupCanReadContent -> contentSummaryDtos.addAll(userGroupCanReadContent.getContentType().getContents().stream()
                                .map(ContentSummaryDto::new).collect(Collectors.toList()))));
        return contentSummaryDtos;
    }

    public JsonView delete(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkDeleteRight(content, user);
        content.setDeleteState();
        contentRepository.save(content);
        return successJsonResponse();
    }


    public JsonView listSave(ContentsDto contents, User user) {
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());
        lectureAuthority.checkUpdateRight(lecture, user);
        contents.getContents().forEach(contentParameterDto -> {
            contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        });
        return successJsonResponse();
    }
}
