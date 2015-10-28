package org.next.infra.user.repository;

import org.next.infra.user.domain.AuthorityType;
import org.next.infra.user.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityType(AuthorityType authorityType);
}
