package org.next.lms.content.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import org.next.config.AppConfig;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.domain.QContent;

@Getter
@Setter
public class ContentLimitDao extends ContentDao {

    private Long page = 0L;
    private Long writer;
    private ContentType contentType;

    @Override
    protected JPAQuery limitPolicy(JPAQuery query) {
        QContent qContent = QContent.content;
        Long offset = page * AppConfig.pageSize;
        if (writer != null)
            query = query.where(qContent.writer.id.eq(writer));
        if (contentType != null)
            query = query.where(qContent.contentGroup.contentType.eq(contentType));
        return query.limit(AppConfig.pageSize).offset(offset);
    }

}
