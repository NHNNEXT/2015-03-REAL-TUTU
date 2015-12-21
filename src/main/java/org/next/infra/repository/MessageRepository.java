package org.next.infra.repository;

import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverId(Long userId, org.springframework.data.domain.Pageable request);

    Message findByReceiverIdAndTypeAndPkAtBelongTypeTable(Long userId, MessageType type, Long pkAtBelongTypeTable);
}
