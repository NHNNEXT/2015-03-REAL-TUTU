package org.next.lms.like;

import lombok.*;
import org.next.lms.user.User;
import org.next.lms.reply.Reply;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_REPLY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "REPLY_ID"})
})
public class UserLikesReply {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesReply(User user, Reply reply) {
        this.user = user;
        this.reply = reply;
    }
}