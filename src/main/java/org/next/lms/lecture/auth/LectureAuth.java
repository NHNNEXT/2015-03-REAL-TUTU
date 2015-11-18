package org.next.lms.lecture.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.user.User;
import org.next.lms.lecture.Lecture;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class LectureAuth extends Auth {
    public void checkUpdateRight(Lecture lecture, User user) {
        rightCheck(lecture.getHostUser().equals(user));
    }

    public void checkDeleteRight(User user, Lecture lecture) {
        rightCheck(lecture.getHostUser().equals(user));
    }

    public void checkApprovalRight(User user, Lecture lecture) {
        rightCheck(lecture.getHostUser().equals(user));
    }

    public void checkGroupChangeRight(User user, Lecture lecture) {
        rightCheck(lecture.getHostUser().equals(user));
    }
}
