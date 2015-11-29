package org.next.infra.repository;

import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.submit.UserHaveToSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHaveToSubmitRepository extends JpaRepository<UserHaveToSubmit, Long> {
}
