package org.next.lms.repository;

import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
