package org.next.lms.lecture.control.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class LectureAuth extends CRUDBasicAuthCheck {

    public void checkApprovalRight(User user, Lecture lecture) {
        rightCheck(isObjectOwner(lecture, user));
    }

    public void checkGroupChangeRight(User user, Lecture lecture) {
        rightCheck(isObjectOwner(lecture, user));
    }

    public void checkExpelRight(Lecture lecture, Long userId, User user) {
        rightCheck(isObjectOwner(lecture, user) && !userId.equals(user.getId()) || !isObjectOwner(lecture, user) && userId.equals(user.getId()));
    }
}
