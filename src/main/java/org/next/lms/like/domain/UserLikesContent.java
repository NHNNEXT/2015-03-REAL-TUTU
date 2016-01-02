package org.next.lms.like.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.lms.content.domain.Content;
import org.next.lms.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "USER_LIKES_CONTENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "CONTENT_ID"})
})
public class UserLikesContent {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesContent(User user, Content content) {
        this.user = user;
        this.content = content;
    }

}
