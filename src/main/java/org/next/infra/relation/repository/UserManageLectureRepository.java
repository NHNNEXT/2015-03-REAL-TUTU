package org.next.infra.relation.repository;

import org.next.infra.relation.UserManageLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManageLectureRepository extends JpaRepository<UserManageLecture, Long> {
    void deleteByLectureId(Long id);
}
