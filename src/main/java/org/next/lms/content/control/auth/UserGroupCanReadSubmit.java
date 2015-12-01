package org.next.lms.content.control.auth;


import lombok.*;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.domain.UserGroup;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"userGroup", "contentGroup"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userGroup", "contentGroup"})
@Entity
@Table(name = "USER_GROUP_CAN_READ_TODO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_GROUP_ID", "CONTENT_TYPE_ID"})
})
public class UserGroupCanReadSubmit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TYPE_ID")
    private ContentGroup contentGroup;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
