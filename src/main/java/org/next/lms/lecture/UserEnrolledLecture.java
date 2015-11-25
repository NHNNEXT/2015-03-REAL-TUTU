package org.next.lms.lecture;

import lombok.*;
import org.next.lms.user.User;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Integer approvalState;

    // TODO 사이드바 표시 여부를 나타내는 flag라면
    // display on sidebar
    // show on sidebar 와 같은 변수명을 사용해보면 어떨까요?
    @Column(name = "SIDE_MENU")
    private Boolean sideMenu;

    public void toggleSideMenu() {
        if (this.sideMenu == null) {
            this.sideMenu = true;
            return;
        }
        this.sideMenu = !this.sideMenu;
    }

    public void showLectureOnSideBar(){
        this.sideMenu = true;
    }
}
