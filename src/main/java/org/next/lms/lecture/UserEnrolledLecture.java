package org.next.lms.lecture;

import lombok.*;
import org.next.lms.user.User;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"lecture", "user"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lecture", "user"})
@Entity
@Table(name = "USER_ENROLLED_LECTURE")
public class UserEnrolledLecture {

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Id
    @Column(name = "USER_ENROLLED_LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "APPROVAL_STATE")
    private Integer approvalState;

    @Column(name = "SIDE_MENU")
    private Boolean sideMenu;

    public void sideMenuToggle() {
        if (this.sideMenu == null) {
            this.sideMenu = true;
            return;
        }
        this.sideMenu = !this.sideMenu;
    }
}
