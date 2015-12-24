package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.QContent;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public abstract class ContentDao {

    protected Long lectureId;
    protected String keyword;
    QContent qContent = QContent.content;

    public List<Content> getList(EntityManager entityManager) {
        JPAQuery query = getJpaQuery(entityManager);
        query = limitPolicy(query);
        return query.list(qContent);
    }

    public long getSize(EntityManager entityManager) {
        JPAQuery query = getJpaQuery(entityManager);
        return query.count();
    }

    private JPAQuery getJpaQuery(EntityManager entityManager) {
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent);
        if (this.lectureId != null)
            query = query.where(qContent.lecture.id.eq(this.lectureId));
        if (this.keyword != null)
            query = query.where(qContent.title.like(getLikeExpression(this.keyword)).or(qContent.body.like(getLikeExpression(this.keyword))));
        return query;
    }

    protected abstract JPAQuery limitPolicy(JPAQuery query);


}
