package org.next.infra.repository;

import org.next.lms.content.domain.ContentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentGroupRepository extends JpaRepository<ContentGroup, Long> {
}
