package org.next.infra.repository;

import org.next.lms.content.domain.ContentLinkContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface ContentLinkContentRepository extends JpaRepository<ContentLinkContent, Long> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "contentRelation-by-linkContentId")
    })
    ContentLinkContent findByLinkContentIdAndLinkedContentId(Long linkContentId, Long linkedContentId);

    void deleteByLinkContentIdAndLinkedContentId(Long linkContentId, Long linkedContentId);
}
