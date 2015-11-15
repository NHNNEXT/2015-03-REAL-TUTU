package org.next.lms.lecture.right;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"userGroup", "contentType"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userGroup", "contentType"})
@Entity
@Table(name = "USER_GROUP_CAN_WRITE_CONTENT")
public class UserGroupCanWriteContent {

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
