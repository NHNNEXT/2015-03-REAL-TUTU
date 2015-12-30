package org.next.lms.submit.domain;

import lombok.Getter;
import org.next.infra.uploadfile.dto.UploadedFileDto;
import org.next.lms.user.domain.UserSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SubmitDto {

    private final Long id;
    private final String body;
    private final Date writeDate;
    private final Date updateTime;
    private final UserSummaryDto writer;
    private final List<UploadedFileDto> attachments;

    public SubmitDto(Submit submit) {
        this.id = submit.getId();
        this.body = submit.getBody();
        this.writeDate = submit.getWriteDate();
        this.updateTime = submit.getUpdateTime();
        this.writer = new UserSummaryDto(submit.getWriter());
        this.attachments = submit.getAttachments().stream().map(UploadedFileDto::new).collect(Collectors.toList());
    }
}
