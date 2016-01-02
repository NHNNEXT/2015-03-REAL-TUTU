package org.next.infra.repository;

import org.next.lms.submit.domain.Submit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "submit-by-userHaveToSubmitId")
    })
    List<Submit> findByUserHaveToSubmitId(Long id, PageRequest pageRequest);
}

