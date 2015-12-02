package org.next.lms.tag.control;

import org.next.lms.tag.domain.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class TagDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<String> getTags(String keyword) {
        TypedQuery<String> query = entityManager.createQuery("select distinct t.text from Tag t where t.text like :keyword", String.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

}
