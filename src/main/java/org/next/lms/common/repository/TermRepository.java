package org.next.lms.common.repository;

import org.next.infra.user.domain.Authority;
import org.next.infra.user.domain.AuthorityType;
import org.next.lms.common.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
