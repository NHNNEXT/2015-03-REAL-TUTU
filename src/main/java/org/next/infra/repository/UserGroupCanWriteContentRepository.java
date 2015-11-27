package org.next.infra.repository;

import org.next.lms.lecture.control.auth.UserGroupCanWriteContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupCanWriteContentRepository extends JpaRepository<UserGroupCanWriteContent, Long> {
    void deleteByUserGroupId(Long id);

    void deleteByContentTypeId(Long id);
}
