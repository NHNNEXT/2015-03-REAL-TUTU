package org.next.lms.repository;

import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {
}
