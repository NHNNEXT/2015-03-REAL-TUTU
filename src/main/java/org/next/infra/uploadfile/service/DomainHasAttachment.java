package org.next.infra.uploadfile.service;

import org.next.infra.uploadfile.UploadedFile;

import java.util.List;
import java.util.function.Consumer;

public interface DomainHasAttachment {

    List<UploadedFile> getAttachments();

    Integer getMaxAttachmentSize();

    Consumer<? super UploadedFile> getAttachmentAddAction();

    Consumer<? super UploadedFile> getAttachmentRemoveAction();
}
