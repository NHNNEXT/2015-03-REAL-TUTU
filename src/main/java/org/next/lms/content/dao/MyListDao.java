package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.QContent;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.QLecture;
import org.next.lms.lecture.domain.QUserEnrolledLecture;
import org.next.lms.user.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public class MyListDao {

    protected String keyword;

    public List<Content> getList(EntityManager entityManager, User user) {
        QContent qContent = QContent.content;
        QLecture qLecture = QLecture.lecture;
        QUserEnrolledLecture qUserEnrolledLecture = QUserEnrolledLecture.userEnrolledLecture;
        List<Lecture> enrolledLectures = new JPAQuery(entityManager).from(qUserEnrolledLecture).innerJoin(qUserEnrolledLecture.lecture, qLecture).where(qUserEnrolledLecture.user.eq(user)).list(qLecture);
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent).where(qContent.lecture.in(enrolledLectures)).where(qContent.title.like(getLikeExpression(keyword)).or(qContent.body.like(getLikeExpression(keyword)))).limit(AppConfig.pageSize);
        return query.list(qContent);
    }
}
