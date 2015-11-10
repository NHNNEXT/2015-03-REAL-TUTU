package org.next.infra.relation;

import lombok.*;
import org.next.lms.user.User;
import org.next.lms.lecture.Lesson;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_LESSON")
public class UserLikesLesson {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesLesson(User user, Lesson lesson) {
        this.user = user;
        this.lesson = lesson;
    }
}