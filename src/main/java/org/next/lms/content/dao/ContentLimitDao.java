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
public class ContentLimitDao extends ContentDao {

    private Long page = 0L;

    @Override
    protected JPAQuery limitPolicy(JPAQuery query) {
        Long offset = page * AppConfig.pageSize;
        return query.limit(AppConfig.pageSize).offset(offset);
    }

}
