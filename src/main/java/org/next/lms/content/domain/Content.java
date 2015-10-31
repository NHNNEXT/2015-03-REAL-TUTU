package org.next.lms.content.domain;

import lombok.*;
import org.next.infra.user.domain.LoginAccount;
import org.next.lms.lecture.domain.Lecture;

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

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToOne(mappedBy = "userInfo")
    private LoginAccount writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    // Field
    @Id
    @Column(name = "CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

