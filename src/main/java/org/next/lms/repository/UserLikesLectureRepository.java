package org.next.lms.repository;

import org.next.lms.like.UserLikesContent;
import org.next.lms.like.UserLikesLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesLectureRepository extends JpaRepository<UserLikesLecture, Long> {
}
