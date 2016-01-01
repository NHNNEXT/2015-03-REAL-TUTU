package org.next.infra.repository;

import org.next.lms.letter.Letter;
import org.next.lms.message.domain.Message;
import org.next.lms.message.domain.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
