package org.next.lms.like;

import lombok.*;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "USER_LIKES_LECTURE")
public class UserLikesLecture {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserLikesLecture(UserInfo userInfo, Lecture lecture) {
        this.userInfo = userInfo;
        this.lecture = lecture;
    }
}
