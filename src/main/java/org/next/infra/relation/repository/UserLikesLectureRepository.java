package org.next.infra.relation.repository;

import org.next.infra.relation.UserLikesLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesLectureRepository extends JpaRepository<UserLikesLecture, Long> {
}
