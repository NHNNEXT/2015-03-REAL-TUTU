package org.next.lms.content.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"linkContent", "linkedContent"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"linkContent", "linkedContent"})
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
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
