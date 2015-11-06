package org.next.lms.like;

import lombok.*;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.lecture.domain.Lesson;

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
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesLesson(UserInfo userInfo, Lesson lesson) {
        this.userInfo = userInfo;
        this.lesson = lesson;
    }
}