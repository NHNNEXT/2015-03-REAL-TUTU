package org.next.lms.message.repository;

import org.next.lms.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long userId, org.springframework.data.domain.Pageable request);

}
