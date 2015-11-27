package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.repository.ContentRepository;
import org.next.lms.content.domain.Content;
import org.next.lms.content.control.ContentAuth;
import org.next.lms.lecture.domain.Lecture;
import org.next.infra.repository.ContentTypeRepository;
import org.next.lms.user.domain.User;

import java.util.Date;

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

    public Content saveContent(Lecture lecture, User user, ContentRepository contentRepository, ContentTypeRepository contentTypeRepository, ContentAuth contentAuthority) {
        if (type == null)
            throw new PatternNotMatchedException("게시물 타입을 입력해야합니다.");
        content = new Content();
        content.setLecture(lecture);
        content.setWriter(user);
        content.setType(assureNotNull(contentTypeRepository.findOne(type)));
        contentAuthority.checkWriteRight(content.getType(), user);
        content.setId(this.id);
        content.setTitle(this.title);
        content.setBody(this.body);
        content.setWriteDate(new Date());
        content.setStartTime(this.startTime);
        content.setEndTime(this.endTime);
        content.validateFields();
        contentRepository.save(content);
        return content;
    }

}
