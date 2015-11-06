package org.next.lms.repository;

import org.next.lms.like.UserLikesLesson;
import org.next.lms.like.UserLikesReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesLessonRepository extends JpaRepository<UserLikesLesson, Long> {
}
