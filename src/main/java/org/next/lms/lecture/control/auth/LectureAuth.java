package org.next.lms.lecture.control.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class LectureAuth extends Auth {
    public void checkUpdateRight(Lecture lecture, User user) {
        rightCheck(user.equals(lecture.getHostUser()));
    }

    public void checkDeleteRight(User user, Lecture lecture) {
        rightCheck(user.equals(lecture.getHostUser()));
    }

    public void checkApprovalRight(User user, Lecture lecture) {
        rightCheck(user.equals(lecture.getHostUser()));
    }

    public void checkGroupChangeRight(User user, Lecture lecture) {
        rightCheck(user.equals(lecture.getHostUser()));
    }
}
