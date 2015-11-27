package org.next.infra.repository;

import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {
    UserEnrolledLecture findOneByUserIdAndLectureId(Long userId, Long lectureId);
}
