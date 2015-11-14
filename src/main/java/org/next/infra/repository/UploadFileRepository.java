package org.next.infra.repository;

import org.next.infra.uploadfile.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadedFile, Long> {
    UploadedFile findByUglyFileName(String uglifiedFileName);

    List<UploadedFile> findByUploadUserId(Long id);
}
