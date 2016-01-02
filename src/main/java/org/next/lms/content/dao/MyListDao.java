package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.domain.QContent;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public class MyListDao {

    private String keyword;
    private ContentType contentType;
    private Integer page = 0;

    public List<Content> getList(EntityManager entityManager, List<Lecture> enrolledLectures) {
        QContent qContent = QContent.content;
        JPAQuery query = new JPAQuery(entityManager);

        query = query.from(qContent).where(qContent.lecture.in(enrolledLectures)).limit(AppConfig.PAGE_SIZE).offset(AppConfig.PAGE_SIZE * page).orderBy(qContent.id.desc());
        if (keyword != null)
            query = query.where(qContent.title.like(getLikeExpression(keyword)).or(qContent.body.like(getLikeExpression(keyword))));
        if (contentType != null)
            query = query.where(qContent.contentGroup.contentType.eq(contentType));

        query.setHint("org.hibernate.cacheable", true);
        return query.list(qContent);
    }
}
