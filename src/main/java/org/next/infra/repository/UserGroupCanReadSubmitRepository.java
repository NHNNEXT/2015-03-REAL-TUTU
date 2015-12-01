package org.next.infra.repository;

import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupCanReadSubmitRepository extends JpaRepository<UserGroupCanReadSubmit, Long> {
    void deleteByUserGroupId(Long id);

    void deleteByContentGroupId(Long id);
}
