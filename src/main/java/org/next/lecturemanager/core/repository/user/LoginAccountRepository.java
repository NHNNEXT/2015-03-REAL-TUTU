package org.next.lecturemanager.core.repository.user;

import org.next.lecturemanager.core.domain.user.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAccountRepository extends JpaRepository<LoginAccount, Long> {
    LoginAccount findByEmailId(String email);
}
