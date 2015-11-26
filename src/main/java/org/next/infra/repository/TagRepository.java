package org.next.infra.repository;


import org.next.lms.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findDistinctTextByTextContaining(String keyword);

    void deleteByContentId(Long id);
}
