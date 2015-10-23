package org.next.lecturemanager.core.repository.user;

import org.next.lecturemanager.core.domain.user.AuthorityType;
import org.next.lecturemanager.core.domain.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityType(AuthorityType authorityType);
}
