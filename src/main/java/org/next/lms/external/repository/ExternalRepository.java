package org.next.lms.external.repository;

import org.next.lms.content.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalRepository extends JpaRepository<ContentType, Long> {
}
