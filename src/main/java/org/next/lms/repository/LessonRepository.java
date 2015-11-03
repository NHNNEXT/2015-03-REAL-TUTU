package org.next.lms.repository;

import org.next.lms.lecture.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by park on 15. 11. 3..
 */
public interface LessonRepository extends JpaRepository <Lesson, Long> {
}
