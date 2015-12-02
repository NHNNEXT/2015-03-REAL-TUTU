package org.next.lms.content.relative;

import lombok.*;
import org.next.lms.content.domain.Content;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"linkContent", "linkedContent"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"linkContent", "linkedContent"})
@Entity
@Table(name = "CONTENT_LINK_CONTENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LINK_CONTENT_ID", "LINKED_CONTENT_ID"})
})
public class ContentLinkContent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINK_CONTENT_ID")
    private Content linkContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINKED_CONTENT_ID")
    private Content linkedContent;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
