package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.domain.QContent;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public abstract class ContentDao {

    protected Long lectureId;
    protected String keyword;
    private Long writer;
    private ContentType contentType;
    private Long contentGroupId;
    private Long likeUser;
    private String contentGroupName;
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
        if (writer != null)
            query = query.where(qContent.writer.id.eq(writer));
        if (contentType != null)
            query = query.where(qContent.contentGroup.contentType.eq(contentType));
        if (contentGroupId != null)
            query = query.where(qContent.contentGroup.id.eq(contentGroupId));
        if (contentGroupName != null && !"".equals(contentGroupName))
            query = query.where(qContent.contentGroup.name.eq(contentGroupName));
        if (this.likeUser != null)
            query = query.where(qContent.userLikesContents.any().user.id.eq(likeUser));
        query.setHint("org.hibernate.cacheable", true);
        return query;
    }

    protected abstract JPAQuery limitPolicy(JPAQuery query);


}
