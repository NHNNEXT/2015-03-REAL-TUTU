package org.next.lms.content;

import lombok.*;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.reply.Reply;
import org.next.lms.lecture.Lecture;
import org.next.infra.relation.UserLikesContent;
import org.next.lms.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"attachment", "likes", "replies", "writer", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"attachment", "likes", "replies", "writer", "lecture"})
@Entity
@Table(name = "CONTENT")
public class Content {

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<UserLikesContent> likes = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UploadedFile> attachment = new ArrayList<>();

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "hits")
    private Long hits;

    @Column(name = "TYPE")
    private Integer type;

    @Lob
    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DUE_DATE")
    private Date dueDate;

    public void update(Content content) {
        if (content.title != null)
            this.title = content.title;
        if (content.body != null)
            this.body = content.body;
        if (content.dueDate != null)
            this.dueDate = content.dueDate;
        if (content.type != null)
            this.type = content.type;
    }

    public void hits() {
        if (hits == null)
            hits = 0L;
        hits++;
    }

    public void setDeleteState() {
        this.writer = null;
        this.lecture = null;
    }
}

