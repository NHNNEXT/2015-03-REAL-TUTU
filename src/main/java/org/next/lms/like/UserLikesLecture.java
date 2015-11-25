package org.next.lms.like;

import lombok.*;
import org.next.lms.lecture.Lecture;
import org.next.lms.user.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_LECTURE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "LECTURE_ID"})
})
public class UserLikesLecture {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesLecture(User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
    }
}
