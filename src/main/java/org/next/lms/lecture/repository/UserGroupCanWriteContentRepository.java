package org.next.lms.lecture.repository;

import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupCanWriteContentRepository extends JpaRepository<UserGroupCanWriteContent, Long> {
    void deleteByUserGroupId(Long id);

    void deleteByContentTypeId(Long id);
}
