package org.next.lms.repository;

import org.next.lms.lecture.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository <Lesson, Long> {
    void deleteByLectureId(Long id);
}
