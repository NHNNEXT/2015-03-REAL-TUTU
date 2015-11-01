package org.next.lms.repository;

import org.next.infra.user.domain.Authority;
import org.next.infra.user.domain.AuthorityType;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
