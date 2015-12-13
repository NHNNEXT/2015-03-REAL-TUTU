package org.next.infra.repository;

import org.next.lms.lecture.domain.LectureNodeHasLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureNodeHasLectureRepository extends JpaRepository<LectureNodeHasLecture, Long> {
    List<LectureNodeHasLecture> findByLectureNodeId(Long lectureNodeId);

    void deleteByLectureNodeIdAndLectureId(Long nodeId, Long lectureId);
}
