package org.next.lms.content.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.next.infra.user.domain.Authority;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"lecture", "userInfo"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lecture", "userInfo"})
@Entity
@Table(name = "USER_MANAGE_LECTURE")
public class UserManageLecture {

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_INFO_ID")
    private UserInfo userInfo;

    @Id
    @Column(name = "USER_HOST_LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
