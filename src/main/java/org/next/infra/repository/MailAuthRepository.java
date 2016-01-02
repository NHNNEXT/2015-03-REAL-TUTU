package org.next.infra.repository;

import org.next.infra.mail.MailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailAuthRepository extends JpaRepository<MailAuth, Long> {
    MailAuth findByEmail(String email);
}