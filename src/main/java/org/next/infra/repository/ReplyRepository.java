package org.next.infra.repository;

import org.next.lms.reply.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @QueryHints(value = {
        @QueryHint(name = "org.hibernate.cacheable", value = "true"),
        @QueryHint(name = "org.hibernate.cacheRegion", value = "content-by-contentId")
    })
    List<Reply> findByContentId(Long contentId, Pageable pageable);

    void deleteByContentId(Long id);
}

