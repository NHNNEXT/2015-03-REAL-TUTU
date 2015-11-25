package org.next.lms.tag.repository;


import org.next.lms.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByTextContaining(String keyword);

    void deleteByContentId(Long id);
}
