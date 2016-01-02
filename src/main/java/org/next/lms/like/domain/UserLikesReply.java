package org.next.lms.like.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
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