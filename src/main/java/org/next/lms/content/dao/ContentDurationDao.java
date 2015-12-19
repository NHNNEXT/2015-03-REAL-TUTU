package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.infra.exception.WrongAccessException;
import org.next.lms.content.domain.QContent;

import java.util.Date;

@Getter
@Setter
public class ContentDurationDao extends ContentDao {

    private Long start;
    private Long end;

    @Override
    protected JPAQuery limitPolicy(JPAQuery query) {
        QContent qContent = QContent.content;
        if (start == null || end == null)
            throw new WrongAccessException();
        Date start = new Date(this.start);
        Date end = new Date(this.end);
        return query.where(
                qContent.startTime.between(start, end).or(qContent.endTime.between(start, end))
                        .or(qContent.startTime.before(start).and(qContent.endTime.after(end)))
        );
    }

}
