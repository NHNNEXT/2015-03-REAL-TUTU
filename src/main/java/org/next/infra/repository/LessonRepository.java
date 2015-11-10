package org.next.infra.repository;

import org.next.lms.lecture.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository <Lesson, Long> {
    void deleteByLectureId(Long id);
}
