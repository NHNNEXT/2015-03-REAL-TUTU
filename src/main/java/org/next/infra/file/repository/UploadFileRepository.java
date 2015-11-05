package org.next.infra.file.repository;

import org.next.infra.file.domain.UploadedFile;
import org.next.infra.user.domain.Authority;
import org.next.infra.user.domain.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadedFile, Long> {
    UploadedFile findByUglyFileName(String uglifiedFileName);
}
