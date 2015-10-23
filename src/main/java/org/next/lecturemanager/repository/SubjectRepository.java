package org.next.lecturemanager.repository;

import org.next.lecturemanager.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
