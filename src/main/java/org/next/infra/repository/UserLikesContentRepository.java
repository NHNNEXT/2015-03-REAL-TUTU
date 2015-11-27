package org.next.infra.repository;

import org.next.lms.like.domain.UserLikesContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesContentRepository extends JpaRepository<UserLikesContent, Long> {
}
