package org.next.infra.repository;


import org.next.lms.tag.domain.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteByContentId(Long id);
}
