package org.next.infra.relation.repository;

import org.next.infra.relation.UserEnrolledLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {
}
