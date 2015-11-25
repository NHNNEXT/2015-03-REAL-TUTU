package org.next.lms.like;

import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.user.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
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
