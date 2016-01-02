package org.next.infra.repository;

import org.next.lms.lecture.domain.LectureNodeHasLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface LectureNodeHasLectureRepository extends JpaRepository<LectureNodeHasLecture, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "lectureNode-by-lectureNodeId")
    })
    List<LectureNodeHasLecture> findByLectureNodeId(Long lectureNodeId);

    void deleteByLectureNodeIdAndLectureId(Long nodeId, Long lectureId);
}
