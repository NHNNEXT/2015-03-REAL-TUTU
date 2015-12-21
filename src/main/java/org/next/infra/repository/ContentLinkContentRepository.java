package org.next.infra.repository;

import org.next.lms.content.relative.ContentLinkContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentLinkContentRepository extends JpaRepository<ContentLinkContent, Long> {
    ContentLinkContent findByLinkContentIdAndLinkedContentId(Long linkContentId, Long linkedContentId);

    void deleteByLinkContentIdAndLinkedContentId(Long linkContentId, Long linkedContentId);
}
