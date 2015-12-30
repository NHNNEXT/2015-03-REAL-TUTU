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
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ComingScheduleDao extends MyListDao {

    private Integer page = 0;

    public List<Content> getList(EntityManager entityManager, List<Lecture> enrolledLectures) {
        QContent qContent = QContent.content;
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent).where(qContent.lecture.in(enrolledLectures)).limit(AppConfig.PAGE_SIZE).offset(AppConfig.PAGE_SIZE * page);
        query = query.where(qContent.contentGroup.contentType.eq(ContentType.SCHEDULE));
        Date now = new Date();
        query = query.where(qContent.endTime.after(now)).orderBy(qContent.startTime.asc());
        return query.list(qContent);
    }
}
