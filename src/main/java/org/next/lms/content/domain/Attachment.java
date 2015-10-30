package org.next.lms.content.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"content"})
@Entity
@Table(name = "ATTACHMENT")
public class Attachment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @Id
    @Column(name = "ATTACHMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "URL")
    private String url;

    @Column(name = "THUMBNAIL_URL")
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATTACHMENT_TYPE")
    private AttachmentType type;
}
