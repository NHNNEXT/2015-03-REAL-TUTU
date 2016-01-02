package org.next.infra.repository;

import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "messages-by-receiverId")
    })
    List<Message> findByReceiverId(Long userId, org.springframework.data.domain.Pageable request);

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "messaeg-by-receiverIdAndTypeAndPkAtBelongTypeTable")
    })
    Message findByReceiverIdAndTypeAndPkAtBelongTypeTable(Long userId, MessageType type, Long pkAtBelongTypeTable);

    Long countByReceiverIdAndType(Long id, MessageType newLetterArrived);
}
