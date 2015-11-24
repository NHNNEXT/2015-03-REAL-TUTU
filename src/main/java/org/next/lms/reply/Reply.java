package org.next.lms.reply;

import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.like.UserLikesReply;
import org.next.lms.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"content", "userLikesReplies", "writer"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"content", "userLikesReplies", "writer"})
@Entity
@Table(name = "Reply")
public class Reply {

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY)
    private List<UserLikesReply> userLikesReplies = new ArrayList<>();

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

    @NotNull(message = "내용을 입력해주세요.")
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
