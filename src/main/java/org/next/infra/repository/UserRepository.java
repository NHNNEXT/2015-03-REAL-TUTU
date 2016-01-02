package org.next.infra.repository;

import org.next.lms.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "user-by-email")
    })
    User findByEmail(String email);

    List<User> findByNameContaining(String keyword);
}
