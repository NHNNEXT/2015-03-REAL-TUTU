package org.next.lecturemanager.core.repository.user;

import org.next.lecturemanager.core.domain.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
