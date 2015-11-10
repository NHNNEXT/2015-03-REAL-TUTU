package org.next.infra.relation.repository;

import org.next.infra.relation.UserLikesLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesLessonRepository extends JpaRepository<UserLikesLesson, Long> {
}
