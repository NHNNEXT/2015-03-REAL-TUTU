package org.next.infra.repository;

import org.next.infra.uploadfile.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadedFile, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "uploadedFile-by-uglifiedFileName")
    })
    UploadedFile findByUglyFileName(String uglifiedFileName);

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "uploadedFileList-by-userId")
    })
    List<UploadedFile> findByUploadUserId(Long id);
}
