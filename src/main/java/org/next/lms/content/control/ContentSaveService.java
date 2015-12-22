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
import org.next.lms.message.domain.PackagedMessage;
import org.next.lms.message.template.NewContentCreatedMessage;
import org.next.lms.message.template.NewSubmitAssignedMessage;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.next.infra.result.Result.success;
import static org.next.infra.util.CommonUtils.assureNotNull;
import static org.next.lms.message.domain.MessageBuilder.aMessage;

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

        PackagedMessage message = aMessage().from(user).to(content.getLecture().getUserEnrolledLectures().stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList()))
                .with(new NewContentCreatedMessage(content.getLecture())).packaging();

        messageService.send(message);

        if (ContentType.SUBMIT.equals(content.getContentGroup().getContentType())) {
            List<User> submitRequiredUserList = new ArrayList<>();
            contentParameterDto.getSubmitRequiredUsers().stream().forEach(userId -> {
                submitRequiredUserList.add(userRepository.findOne(userId));
            });

            PackagedMessage submitAssignedNoticeMessage = aMessage().from(user).to(submitRequiredUserList)
                    .with(new NewSubmitAssignedMessage(content.getLecture())).packaging();
            messageService.send(submitAssignedNoticeMessage);
        }

        return success(new ContentDto(content, user));
    }

    public Result update(ContentParameterDto contentParameterDto, User user) {
        Content contentFromDB = assureNotNull(contentRepository.findOne(contentParameterDto.getId()));
        contentAuthority.checkUpdateRight(contentFromDB, user);
        contentParameterDto.setProperties(contentFromDB);
        contentFromDB.validate();
        if (ContentType.SUBMIT.equals(contentFromDB.getContentGroup().getContentType())) {
            removeDeletedUser(contentParameterDto, contentFromDB);
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
        content.setContentGroup(assureNotNull(contentGroupRepository.findOne(contentParameterDto.getContentGroup())));
        contentParameterDto.setProperties(content);
        content.setLecture(lecture);
        content.setWriter(user);
        content.validate();
        contentAuthority.checkWriteRight(content.getContentGroup(), user);
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
        if (content.getAttachments() != null)
            content.getAttachments().stream()
                    .filter(attachment -> !contentParameterDto.getAttachments().stream()
                            .filter(id -> attachment.getId().equals(id)).findAny().isPresent()).forEach(attachment -> attachment.setContent(null));

        if (contentParameterDto.getAttachments() != null)
            contentParameterDto.getAttachments().forEach(id -> {
                if (content.getAttachments().stream().filter(attachment -> attachment.getId().equals(id)).findAny().isPresent())
                    return;
                UploadedFile file = uploadFileRepository.findOne(id);
                file.setContent(content);
                uploadFileRepository.save(file);
            });
    }


    private void removeDeletedUser(ContentParameterDto contentParameterDto, Content content) {
        List<UserHaveToSubmit> userHaveToSubmits = content.getUserHaveToSubmits().stream().filter(userHaveToSubmit -> !contentParameterDto.getSubmitRequiredUsers().contains(userHaveToSubmit.getUser().getId())).collect(Collectors.toList());
        content.getUserHaveToSubmits().removeAll(userHaveToSubmits);
        userHaveToSubmitRepository.delete(userHaveToSubmits);
    }

    private void submitUserDeclare(ContentParameterDto contentParameterDto, Content content) {
        if (contentParameterDto.getSubmitRequiredUsers() != null)
            contentParameterDto.getSubmitRequiredUsers().forEach(userId -> {
                if (content.getUserHaveToSubmits().stream().filter(userHaveToSubmit -> userHaveToSubmit.getUser().getId().equals(userId)).findAny().isPresent())
                    return;
                User user = assureNotNull(userRepository.findOne(userId));
                UserHaveToSubmit userHaveToSubmit = new UserHaveToSubmit();
                userHaveToSubmit.setUser(user);
                userHaveToSubmit.setContent(content);
                userHaveToSubmitRepository.save(userHaveToSubmit);
            });
    }

}
