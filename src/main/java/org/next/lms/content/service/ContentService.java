package org.next.lms.content.service;

import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.LectureRepository;
import org.next.infra.result.Result;
import org.next.lms.content.Content;
import org.next.lms.content.auth.ContentAuth;
import org.next.lms.content.dto.ContentDto;
import org.next.lms.content.dto.ContentParameterDto;
import org.next.lms.content.dto.ContentSummaryDto;
import org.next.lms.content.dto.ContentsDto;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.lecture.auth.LectureAuth;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.message.MessageService;
import org.next.lms.message.template.newContentMessageTemplate;
import org.next.lms.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
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

    public Result getDtoById(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkReadRight(content, user);
        content.hits();
        contentRepository.save(content);
        return success(new ContentDto(content, user));
    }

    public Result save(ContentParameterDto contentParameterDto, User user, Long lectureId) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        Content content = contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        messageService.newMessage(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()), new newContentMessageTemplate());
        return success(new ContentDto(content, user));
    }

    public Result update(Content content, User user) {
        Content fromDB = contentRepository.findOne(content.getId());
        fromDB.update(content);
        contentAuthority.checkUpdateRight(fromDB, user);
        content.fieldCheck();
        contentRepository.save(fromDB);
        return success();
    }

    public Result getList(User user) {
        List<ContentSummaryDto> contentSummaryDtos = new ArrayList<>();
        user.getEnrolledLectures().forEach(
                userEnrolledLecture -> userEnrolledLecture.getUserGroup().getReadable()
                        .forEach(userGroupCanReadContent -> contentSummaryDtos.addAll(userGroupCanReadContent.getContentType().getContents().stream()
                                .map(ContentSummaryDto::new).collect(Collectors.toList()))));
        return success(contentSummaryDtos);
    }

    public Result delete(Long id, User user) {
        Content content = assureNotNull(contentRepository.findOne(id));
        contentAuthority.checkDeleteRight(content, user);
        content.setDeleteState();
        contentRepository.save(content);
        return success();
    }


    public Result listSave(ContentsDto contents, User user) {
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());
        lectureAuthority.checkUpdateRight(lecture, user);
        contents.getContents().forEach(contentParameterDto -> {
            contentParameterDto.saveContent(lecture, user, contentRepository, contentTypeRepository, contentAuthority);
        });
        return success();
    }
}
