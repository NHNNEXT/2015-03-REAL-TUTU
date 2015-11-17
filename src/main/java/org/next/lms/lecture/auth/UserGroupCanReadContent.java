package org.next.lms.lecture.auth;

import lombok.*;
import org.next.lms.content.ContentType;
import org.next.lms.lecture.UserGroup;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"userGroup", "contentType"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userGroup", "contentType"})
@Entity
@Table(name = "USER_GROUP_CAN_READ_CONTENT")
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
