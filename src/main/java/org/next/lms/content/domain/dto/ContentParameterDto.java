package org.next.lms.content.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.uploadfile.service.DtoHasAttachment;
import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContentParameterDto implements DtoHasAttachment {

    private Long lectureId;
    private Lecture lecture;
    private Long id;
    private String title;
    private String body;
    private Date writeDate;
    private Date startTime;
    private Date endTime;
    private Long contentGroup;
//    private Content content;
    private boolean tagBlock;
    private boolean relativeBlock;
    private List<Long> submitRequires;
    private List<Long> attachments;

    public void setProperties(Content content) {
        content.setId(id);
        content.setTitle(title);
        content.setBody(body);
        content.setWriteDate(new Date());
        content.setStartTime(startTime);
        content.setEndTime(endTime);
        content.setTagBlock(tagBlock);
        content.setRelativeBlock(relativeBlock);
    }
}
