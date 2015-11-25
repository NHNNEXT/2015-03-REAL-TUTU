package org.next.lms.external.repository;

import org.next.lms.external.External;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalRepository extends JpaRepository<External, Long> {
}
