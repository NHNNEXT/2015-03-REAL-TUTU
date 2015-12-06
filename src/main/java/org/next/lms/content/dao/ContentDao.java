package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.QContent;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.next.infra.util.CommonUtils.getLikeExpression;

@Getter
@Setter
public class ContentDao {

    private Long lectureId;
    private String keyword;
    private Long start;
    private Long end;

    public List<Content> getList(EntityManager entityManager) {
        QContent qContent = QContent.content;
        JPAQuery query = new JPAQuery(entityManager);
        query = query.from(qContent);
        if (this.lectureId != null)
            query = query.where(qContent.lecture.id.eq(this.lectureId));
        if (this.keyword != null)
            query = query.where(qContent.title.like(getLikeExpression(this.keyword)).or(qContent.body.like(getLikeExpression(this.keyword)))).limit(AppConfig.pageSize);
        if (this.start != null && this.end != null){
            Date start = new Date(this.start);
            Date end = new Date(this.end);
            query = query.where(qContent.startTime.between(start, end).or(qContent.endTime.between(start, end).or(qContent.writeDate.between(start, end))));
        }


        // [TODO] 리미트 정책 case 1 : 클라이언트에서 리미트를 요청할 경우 -> 리미트를 큰 숫자로 해서 마음대로 요청할 수 있음
        //                 case 2 : 리미트를 강제적으로 모두 걸 경우 -> date로 between 검색시 기간 내에 이벤트가 있음에도 불구하고 limit에 걸려 내려가지 않을 수 있음 : 마감기한을 놓칠 수 있음.
        //                 case 3 : 키워드 검색시만 강제적으로 리미트 설정 -> 서버스펙만 제대로 알면 마음대로 부하가 큰 요청을 할 수 있음. [현재상태]

        return query.list(qContent);
    }

}
