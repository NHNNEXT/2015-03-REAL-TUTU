package org.next.lms.lecture.repository;

import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupCanReadContentRepository extends JpaRepository<UserGroupCanReadContent, Long> {
    void deleteByUserGroupId(Long id);

    void deleteByContentTypeId(Long id);
}
