package org.next.infra.uploadfile.service;

import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.exception.WrongAccessException;
import org.next.infra.repository.UploadFileRepository;
import org.next.infra.uploadfile.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class AttachmentDeclareService {

    @Autowired
    UploadFileRepository uploadFileRepository;

    public void attachmentsDeclare(DtoHasAttachment dtoHasAttachment, DomainHasAttachment domainHasAttachment) {
        List<Long> submitted = dtoHasAttachment.getAttachments();
        List<UploadedFile> originalAttachments = domainHasAttachment.getAttachments();
        Consumer<? super UploadedFile> action = domainHasAttachment.getAttachmentAddAction();
        Consumer<? super UploadedFile> removeAction = domainHasAttachment.getAttachmentRemoveAction();
        if (submitted == null)
            return;
        if (originalAttachments == null)
            throw new WrongAccessException();
        Integer maxSize = domainHasAttachment.getMaxAttachmentSize();
        if (submitted.size() > maxSize)
            throw new PatternNotMatchedException("파일은 " + maxSize + "개까지만 첨부 가능합니다.");

        originalAttachments.stream()
                .filter(attachment -> !submitted.stream()
                        .filter(id -> attachment.getId().equals(id)).findAny().isPresent()).forEach(removeAction::accept);

        submitted.forEach(id -> {
            if (originalAttachments.stream().filter(attachment -> attachment.getId().equals(id)).findAny().isPresent())
                return;
            UploadedFile file = uploadFileRepository.findOne(id);
            action.accept(file);
            originalAttachments.add(file);
        });
    }


}
