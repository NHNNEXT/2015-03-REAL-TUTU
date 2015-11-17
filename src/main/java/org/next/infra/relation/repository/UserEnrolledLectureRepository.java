package org.next.infra.relation.repository;

import org.next.lms.lecture.UserEnrolledLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {
}
