package org.next.lms.content.control.auth;

import lombok.*;
import org.next.lms.content.domain.ContentType;
import org.next.lms.lecture.domain.UserGroup;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"userGroup", "contentType"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userGroup", "contentType"})
@Entity
@Table(name = "USER_GROUP_CAN_READ_CONTENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_GROUP_ID", "CONTENT_TYPE_ID"})
})
public class UserGroupCanReadContent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TYPE_ID")
    private ContentType contentType;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
