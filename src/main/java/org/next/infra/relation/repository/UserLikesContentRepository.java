package org.next.infra.relation.repository;

import org.next.infra.relation.UserLikesContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesContentRepository extends JpaRepository<UserLikesContent, Long> {
}
