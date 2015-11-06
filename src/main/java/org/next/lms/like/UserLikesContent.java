package org.next.lms.like;

import lombok.*;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_CONTENT")
public class UserLikesContent {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesContent(UserInfo userInfo, Content content) {
        this.userInfo = userInfo;
        this.content = content;
    }

}
