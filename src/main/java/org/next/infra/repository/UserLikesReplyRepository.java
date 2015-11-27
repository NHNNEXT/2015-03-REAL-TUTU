package org.next.infra.repository;

import org.next.lms.like.domain.UserLikesReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesReplyRepository extends JpaRepository<UserLikesReply, Long> {
}
