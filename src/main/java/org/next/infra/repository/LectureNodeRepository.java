package org.next.infra.repository;

import org.next.lms.lecture.domain.LectureNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureNodeRepository extends JpaRepository<LectureNode, Long> {
    List<LectureNode> findByParentId(Long parentId);
}
