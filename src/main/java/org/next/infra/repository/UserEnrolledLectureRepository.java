package org.next.infra.repository;

import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface UserEnrolledLectureRepository extends JpaRepository<UserEnrolledLecture, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "userEnrolledLecture-by-userAndLectureId")
    })
    UserEnrolledLecture findOneByUserIdAndLectureId(Long userId, Long lectureId);

    void deleteByLectureIdOrUserId(Long lectureId, Long userId);
}
