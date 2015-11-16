package org.next.lms.lecture.repository;

import org.next.lms.lecture.ContentType;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
}
