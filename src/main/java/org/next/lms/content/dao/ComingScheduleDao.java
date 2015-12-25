package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.domain.QContent;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.QLecture;
import org.next.lms.lecture.domain.QUserEnrolledLecture;
import org.next.lms.user.domain.User;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ComingScheduleDao extends MyListDao {

    private Integer page = 0;

    public List<Content> getList(EntityManager entityManager, User user) {
        QContent qContent = QContent.content;
        QLecture qLecture = QLecture.lecture;
        QUserEnrolledLecture qUserEnrolledLecture = QUserEnrolledLecture.userEnrolledLecture;
        List<Lecture> enrolledLectures = new JPAQuery(entityManager).from(qUserEnrolledLecture).innerJoin(qUserEnrolledLecture.lecture, qLecture).where(qUserEnrolledLecture.user.eq(user)).list(qLecture);
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent).where(qContent.lecture.in(enrolledLectures)).limit(AppConfig.pageSize).offset(AppConfig.pageSize * page);
        query = query.where(qContent.contentGroup.contentType.eq(ContentType.SCHEDULE));
        Date now = new Date();
        query = query.where(qContent.endTime.after(now)).orderBy(qContent.startTime.asc());
        return query.list(qContent);
    }
}
