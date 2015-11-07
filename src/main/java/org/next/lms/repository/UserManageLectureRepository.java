package org.next.lms.repository;

import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManageLectureRepository extends JpaRepository<UserManageLecture, Long> {
    void deleteByLectureId(Long id);
}
