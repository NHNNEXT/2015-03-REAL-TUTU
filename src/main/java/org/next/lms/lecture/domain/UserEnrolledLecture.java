package org.next.lms.lecture.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"lecture", "user"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lecture", "user"})
@Entity
@Table(name = "USER_ENROLLED_LECTURE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LECTURE_ID", "USER_ID"})
})
public class UserEnrolledLecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    @Id
    @Column(name = "USER_ENROLLED_LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "APPROVAL_STATE")
    private Integer approvalState = ApprovalState.WAITING_APPROVAL;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "SIDE_MENU")
    private boolean sideMenu = false;

    public void toggleSideMenu() {
        this.sideMenu = !this.sideMenu;
    }

    public void showLectureOnSideBar(){
        this.sideMenu = true;
    }
}
