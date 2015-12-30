package org.next.infra.repository;

import org.next.lms.submit.domain.UserHaveToSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHaveToSubmitRepository extends JpaRepository<UserHaveToSubmit, Long> {
    void deleteByContentId(Long id);
}
