package org.next.lms.content.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"attachment"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"attachment"})
@Entity
@Table(name = "CONTENT")
public class Content {

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachment = new ArrayList<>();

    // Field
    @Id
    @Column(name = "CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "WRITER_ID")
    private Long writerId;

    @Column(name = "LECTURE_ID")
    private Long lectureId;

    @Column(name = "PARENT_CONTENT_ID")
    private Long parentContentId;

    @Column(name = "TITLE")
    private String title;

    @Lob
    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DUE_DATE")
    private Date dueDate;

    @Column(name = "LIKE_COUNT")
    private Long like;
}

