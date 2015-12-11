package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.lecture.domain.Lecture;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContentParameterDto {


    private Long lectureId;
    private Lecture lecture;
    private Long id;
    private Boolean submitCanAttach;
    private String title;
    private String body;
    private Date writeDate;
    private Date startTime;
    private Date endTime;
    private Long contentGroup;
    private Content content;
    private List<Long> submitRequiredUsers;
    private List<Long> attachments;

    public void setProperties(Content content) {
        content.setId(id);
        content.setTitle(title);
        content.setBody(body);
        content.setSubmitCanAttach(submitCanAttach);
        content.setWriteDate(new Date());
        content.setStartTime(startTime);
        content.setEndTime(endTime);
        if(!ContentType.NOTICE.equals(content.getContentGroup().getContentType())) //[TODO 체크]
            return;
        content.setStartTime(content.getWriteDate());
    }
}
