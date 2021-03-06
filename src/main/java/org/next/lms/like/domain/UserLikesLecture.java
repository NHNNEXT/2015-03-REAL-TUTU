package org.next.lms.like.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
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
