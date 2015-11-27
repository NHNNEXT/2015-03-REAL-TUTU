package org.next.infra.repository;

import org.next.lms.like.domain.UserLikesLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesLectureRepository extends JpaRepository<UserLikesLecture, Long> {
}
