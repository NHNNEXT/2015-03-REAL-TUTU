package org.next.lms.content.control;

import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.repository.*;
import org.next.infra.result.Result;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.domain.dto.ContentDto;
import org.next.lms.content.domain.dto.ContentListDto;
import org.next.lms.content.domain.dto.ContentParameterDto;
import org.next.lms.lecture.control.auth.LectureAuth;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.message.control.MessageService;
import org.next.lms.message.template.NewContentCreatedMessage;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;

@Service
@Transactional
public class ContentSaveService {

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
    private LectureAuth lectureAuth;

    @Autowired
    private MessageService messageService;

    @Autowired
    ContentLinkContentRepository contentLinkContentRepository;

    @Autowired
    UploadFileRepository uploadFileRepository;

    public Result saveContents(ContentListDto contents, User user) {
        Lecture lecture = lectureRepository.findOne(contents.getLectureId());

        lectureAuth.checkUpdateRight(lecture, user);

        contents.getContents().forEach(contentParameterDto -> saveContent(contentParameterDto, user));
        return success();
    }


    public Result save(ContentParameterDto contentParameterDto, User user) {
        Content content = saveContent(contentParameterDto, user);

        messageService
                // TODO 여기 1 이라는 숫자 바꿔야 함 아직 메시지 그룹핑 정책이 논의된 바 없어서 임의로 1 적음
                .send(new NewContentCreatedMessage(content.getLecture(), content, 1))
                .to(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()));
        return success(new ContentDto(content, user));
    }

    public Result update(ContentParameterDto contentParameterDto, User user) {
        Content contentFromDB = assureNotNull(contentRepository.findOne(contentParameterDto.getId()));
        contentAuthority.checkUpdateRight(contentFromDB, user);

        contentFromDB.setTitle(contentParameterDto.getTitle());
        contentFromDB.setBody(contentParameterDto.getBody());
        contentFromDB.setWriteDate(new Date());
        contentFromDB.setStartTime(contentParameterDto.getStartTime());
        contentFromDB.setEndTime(contentParameterDto.getEndTime());
        contentFromDB.validate();
        if (ContentType.SUBMIT.equals(contentFromDB.getContentGroup().getContentType())) {
            submitUserDeclare(contentParameterDto, contentFromDB);
        }
        if (contentFromDB.getContentGroup().getAttachment()) {
            attachmentsDeclare(contentParameterDto, contentFromDB);
        }
        return success();
    }


    public Content saveContent(ContentParameterDto contentParameterDto, User user) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(contentParameterDto.getLectureId()));
        if (contentParameterDto.getContentGroup() == null)
            throw new PatternNotMatchedException("게시물 타입을 입력해야합니다.");
        Content content = new Content();
        content.setLecture(lecture);
        content.setWriter(user);
        content.setContentGroup(assureNotNull(contentGroupRepository.findOne(contentParameterDto.getContentGroup())));
        contentAuthority.checkWriteRight(content.getContentGroup(), user);
        content.setId(contentParameterDto.getId());
        content.setTitle(contentParameterDto.getTitle());
        content.setBody(contentParameterDto.getBody());
        content.setWriteDate(new Date());
        content.setStartTime(contentParameterDto.getStartTime());
        content.setEndTime(contentParameterDto.getEndTime());
        content.validate();
        contentRepository.save(content);
        if (ContentType.SUBMIT.equals(content.getContentGroup().getContentType())) {
            submitUserDeclare(contentParameterDto, content);
        }
        if (content.getContentGroup().getAttachment()) {
            attachmentsDeclare(contentParameterDto, content);
        }
        return content;
    }

    private void attachmentsDeclare(ContentParameterDto contentParameterDto, Content content) {
        content.getAttachments().stream()
                .filter(attachment -> !contentParameterDto.getAttachments().stream()
                        .filter(id -> attachment.getId().equals(id)).findAny().isPresent()).forEach(attachment -> attachment.setContent(null));

        contentParameterDto.getAttachments().forEach(id -> {
            if (content.getAttachments().stream().filter(attachment -> attachment.getId().equals(id)).findAny().isPresent())
                return;
            UploadedFile file = uploadFileRepository.findOne(id);
            file.setContent(content);
            uploadFileRepository.save(file);
        });
    }

    private void submitUserDeclare(ContentParameterDto contentParameterDto, Content content) {
        userHaveToSubmitRepository.deleteByContentId(content.getId());
        contentParameterDto.getSubmitRequiredUsers().forEach(userId -> {
            User user = assureNotNull(userRepository.findOne(userId));
            UserHaveToSubmit userHaveToSubmit = new UserHaveToSubmit();
            userHaveToSubmit.setUser(user);
            userHaveToSubmit.setContent(content);
            userHaveToSubmitRepository.save(userHaveToSubmit);
        });
    }

}