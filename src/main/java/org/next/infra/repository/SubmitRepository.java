package org.next.infra.repository;

import org.next.lms.submit.Submit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
}

