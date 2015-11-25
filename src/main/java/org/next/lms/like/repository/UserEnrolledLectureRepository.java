package org.next.lms.like.repository;

import org.next.lms.lecture.UserEnrolledLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {
    UserEnrolledLecture findOneByUserIdAndLectureId(Long userId, Long lectureId);
}
