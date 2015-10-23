package org.next.core.repository.user;

import org.next.core.domain.user.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAccountRepository extends JpaRepository<LoginAccount, Long> {
    LoginAccount findByEmailId(String email);
}
