package org.next.lms.user.repository;

import org.next.lms.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByNameContaining(String keyword);
}
