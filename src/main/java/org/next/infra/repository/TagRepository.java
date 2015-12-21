package org.next.infra.repository;


import org.next.lms.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteByContentId(Long id);
}
