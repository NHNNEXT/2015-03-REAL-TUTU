package org.next.infra.repository;

import org.next.lms.external.domain.External;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalRepository extends JpaRepository<External, Long> {
}
