package org.next.core.user.repository;

import org.next.core.user.domain.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAccountRepository extends JpaRepository<LoginAccount, Long> {
    LoginAccount findByEmailIdAndPassword(String email, String password);
}
