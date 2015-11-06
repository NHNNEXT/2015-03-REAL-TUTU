package org.next.lms.repository;

import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.like.UserLikesContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesContentRepository extends JpaRepository<UserLikesContent, Long> {
}
