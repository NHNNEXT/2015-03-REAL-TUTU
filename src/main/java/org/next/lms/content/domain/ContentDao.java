package org.next.lms.content.domain;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public class ContentDao {

    private Long lectureId;
    private String keyword;

    public List<Content> getList(EntityManager entityManager) {
        QContent qContent = QContent.content;
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent);
        if (this.lectureId != null)
            query.where(qContent.lecture.id.eq(this.lectureId));
        if (this.keyword != null)
            query.where(qContent.title.like(getLikeExpression(this.keyword)).or(qContent.body.like(getLikeExpression(this.keyword))));
        return query.limit(AppConfig.pageSize).list(qContent);
    }

}
