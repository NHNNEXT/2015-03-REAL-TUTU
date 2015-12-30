package org.next.lms.submit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
