package org.next.core.user.repository;

import org.next.core.user.domain.Authority;
import org.next.core.user.domain.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityType(AuthorityType authorityType);
}
