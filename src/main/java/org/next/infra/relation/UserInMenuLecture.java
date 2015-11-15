package org.next.infra.relation;

import lombok.*;
import org.next.lms.user.User;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"lecture", "user"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "USER_MANAGE_LECTURE")
public class UserInMenuLecture {

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_INFO_ID")
    private User user;

    @Id
    @Column(name = "USER_HOST_LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
