package org.next.lms.tag.control;

import lombok.Setter;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;


@Setter
public class TagDao {

    private String keyword;

    public List<String> getList(EntityManager entityManager) {
        return entityManager
                .createQuery("select distinct t.text from Tag t where t.text like :keyword", String.class)
                .setMaxResults(10)
                .setParameter("keyword", getLikeExpression(keyword))
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

}
