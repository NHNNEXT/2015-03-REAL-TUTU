package org.next.lms.reply;

import lombok.*;
import org.next.lms.content.Content;
import org.next.infra.relation.UserLikesReply;
import org.next.lms.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"content", "likes", "writer"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"content", "likes", "writer"})
@Entity
@Table(name = "Reply")
public class Reply {

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY)
    private List<UserLikesReply> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    public void update(Reply reply) {
        this.writeDate = new Date();
        this.body = reply.body;
    }

    public void setDeleteState() {
        this.writer = null;
        this.content = null;
    }
}
