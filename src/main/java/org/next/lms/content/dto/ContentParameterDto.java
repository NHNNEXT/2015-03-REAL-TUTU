package org.next.lms.content.dto;

import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.repository.ContentTypeRepository;

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

    public Content getTypeDeclaredContent(ContentTypeRepository contentTypeRepository) {
        return new Content(this, assureNotNull(contentTypeRepository.findOne(type)));
    }
}

