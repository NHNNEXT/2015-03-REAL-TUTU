package org.next.infra.uploadfile.service;

import org.next.config.AppConfig;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.submit.domain.Submit;
import org.next.lms.submit.domain.SubmitParameterDto;

import java.util.List;

public interface DtoHasAttachment {
    List<Long> getAttachments();

}
