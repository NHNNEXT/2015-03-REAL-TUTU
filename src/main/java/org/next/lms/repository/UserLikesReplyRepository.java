package org.next.lms.repository;

import org.next.lms.like.UserLikesLecture;
import org.next.lms.like.UserLikesReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesReplyRepository extends JpaRepository<UserLikesReply, Long> {
}
