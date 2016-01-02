package org.next.infra.repository;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Override
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    List<Lecture> findAll();
}
