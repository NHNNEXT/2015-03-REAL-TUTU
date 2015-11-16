package org.next.lms.lecture.repository;

import org.next.lms.lecture.ContentType;
import org.next.lms.lecture.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
