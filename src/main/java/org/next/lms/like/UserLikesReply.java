package org.next.lms.like;

import lombok.*;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Reply;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_REPLY")
public class UserLikesReply {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "REPLY_ID")
    private Reply reply;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesReply(UserInfo userInfo, Reply reply) {
        this.userInfo = userInfo;
        this.reply = reply;
    }
}