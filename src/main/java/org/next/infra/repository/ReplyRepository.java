package org.next.infra.repository;

import org.next.lms.reply.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByContentId(Long contentId, Pageable pageable);

    void deleteByContentId(Long id);
}

