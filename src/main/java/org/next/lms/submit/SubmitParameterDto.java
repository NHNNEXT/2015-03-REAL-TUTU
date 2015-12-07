package org.next.lms.submit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubmitParameterDto {

    private Long id;
    private String body;
    private Date writeDate;
    private List<Long> attachments;
    private Long submitId;
}
