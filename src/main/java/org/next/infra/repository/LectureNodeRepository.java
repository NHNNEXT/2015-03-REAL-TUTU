package org.next.infra.repository;

import org.next.lms.lecture.domain.LectureNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface LectureNodeRepository extends JpaRepository<LectureNode, Long> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "lectureNode-by-parentId")
    })
    List<LectureNode> findByParentId(Long parentId);
}
