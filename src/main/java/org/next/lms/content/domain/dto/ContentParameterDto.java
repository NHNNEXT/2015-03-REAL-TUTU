package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.repository.*;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.content.domain.Content;
import org.next.lms.content.control.ContentAuth;
import org.next.lms.content.domain.ContentType;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.user.domain.User;

import java.util.Date;
import java.util.List;

import static org.next.infra.util.CommonUtils.assureNotNull;

@Getter
@Setter
@NoArgsConstructor
public class ContentParameterDto {


    private Long lectureId;
    private Lecture lecture;
    private Long id;
    private String title;
    private String body;
    private Date writeDate;
    private Date startTime;
    private Date endTime;
    private Long contentGroup;
    private Content content;
    private List<Long> submitRequiredUsers;
    private List<Long> attachments;

    public Content saveContent(User user, ContentRepository contentRepository, ContentGroupRepository contentGroupRepository, ContentAuth contentAuthority, UserRepository userRepository, UserHaveToSubmitRepository userHaveToSubmitRepository, UploadFileRepository uploadFileRepository, LectureRepository lectureRepository) {
        Lecture lecture = assureNotNull(lectureRepository.findOne(lectureId));
        if (contentGroup == null)
            throw new PatternNotMatchedException("게시물 타입을 입력해야합니다.");
        content = new Content();
        content.setLecture(lecture);
        content.setWriter(user);
        content.setContentGroup(assureNotNull(contentGroupRepository.findOne(contentGroup)));
        contentAuthority.checkWriteRight(content.getContentGroup(), user);
        content.setId(this.id);
        content.setTitle(this.title);
        content.setBody(this.body);
        content.setWriteDate(new Date());
        content.setStartTime(this.startTime);
        content.setEndTime(this.endTime);
        content.validate();
        contentRepository.save(content);
        if (ContentType.SUBMIT.equals(content.getContentGroup().getContentType())) {
            submitUserDeclare(content, userRepository, userHaveToSubmitRepository);
        }
        if (content.getContentGroup().getAttachment()) {
            attachmentsDeclare(content, uploadFileRepository);
        }
        return content;
    }

    private void attachmentsDeclare(Content content, UploadFileRepository uploadFileRepository) {
        this.attachments.forEach(id->{
            UploadedFile file = uploadFileRepository.findOne(id);
            file.setContent(content);
            uploadFileRepository.save(file);
        });
    }

    private void submitUserDeclare(Content content, UserRepository userRepository, UserHaveToSubmitRepository userHaveToSubmitRepository) {
        this.submitRequiredUsers.forEach(userId -> {
            User user = assureNotNull(userRepository.findOne(userId));
            UserHaveToSubmit userHaveToSubmit = new UserHaveToSubmit();
            userHaveToSubmit.setUser(user);
            userHaveToSubmit.setContent(content);
            userHaveToSubmitRepository.save(userHaveToSubmit);
        });
    }

}
