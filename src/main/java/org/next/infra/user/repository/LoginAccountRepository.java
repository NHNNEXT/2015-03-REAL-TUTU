package org.next.infra.user.repository;

import org.next.infra.user.domain.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAccountRepository extends JpaRepository<LoginAccount, Long> {
    LoginAccount findByEmailId(String email);
}
