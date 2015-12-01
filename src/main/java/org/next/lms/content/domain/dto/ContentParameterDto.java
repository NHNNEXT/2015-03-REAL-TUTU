package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.repository.ContentRepository;
import org.next.infra.repository.UserHaveToSubmitRepository;
import org.next.infra.repository.UserRepository;
import org.next.lms.content.domain.Content;
import org.next.lms.content.control.ContentAuth;
import org.next.lms.lecture.domain.Lecture;
import org.next.infra.repository.ContentGroupRepository;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.user.domain.User;

import java.util.Date;
import java.util.List;

import static org.next.infra.util.CommonUtils.assureNotNull;

@Getter
@Setter
@NoArgsConstructor
public class ContentParameterDto {

    private Lecture lecture;
    private Long id;
    private String title;
    private String body;
    private Date writeDate;
    private Date startTime;
    private Date endTime;
    private Long type;
    private Content content;
    private List<Long> submitRequiredUsers;

    public Content saveContent(Lecture lecture, User user, ContentRepository contentRepository, ContentGroupRepository contentGroupRepository, ContentAuth contentAuthority, UserRepository userRepository, UserHaveToSubmitRepository userHaveToSubmitRepository) {
        if (type == null)
            throw new PatternNotMatchedException("게시물 타입을 입력해야합니다.");
        content = new Content();
        content.setLecture(lecture);
        content.setWriter(user);
        content.setType(assureNotNull(contentGroupRepository.findOne(type)));
        contentAuthority.checkWriteRight(content.getType(), user);
        content.setId(this.id);
        content.setTitle(this.title);
        content.setBody(this.body);
        content.setWriteDate(new Date());
        content.setStartTime(this.startTime);
        content.setEndTime(this.endTime);
        content.validateFields();
        contentRepository.save(content);
        if (content.getType().getSubmit()) {
            submitUserDeclare(content, userRepository, userHaveToSubmitRepository);
        }
        return content;
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
