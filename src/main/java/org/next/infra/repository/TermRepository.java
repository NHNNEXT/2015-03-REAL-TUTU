package org.next.infra.repository;

import org.next.lms.term.Term;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    List<Term> findByNameContaining(String keyword, Pageable pageable);
}
