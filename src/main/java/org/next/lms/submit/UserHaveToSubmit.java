package org.next.lms.submit;

import lombok.*;
import org.next.lms.content.domain.Content;
import org.next.lms.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"user", "content"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "content"})
@Entity
@Table(name = "USER_GROUP_CAN_WRITE_CONTENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "CONTENT_ID"})
})
public class UserHaveToSubmit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
