package org.next.infra.repository;

import org.next.lms.submit.domain.Submit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
    List<Submit> findByUserHaveToSubmitId(Long id, PageRequest pageRequest);
}

