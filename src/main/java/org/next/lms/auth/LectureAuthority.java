package org.next.lms.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.exception.HasNoRightException;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.social.NotAuthorizedException;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class LectureAuthority extends Authority {
    public void checkUpdateRight(Lecture lecture, UserInfo userInfo) {
        rightCheck(lecture.getHostUser().equals(userInfo) || lecture.getManagers().contains(userInfo) || lecture.getEnrolledStudent().stream().anyMatch(relation -> relation.getUserInfo().equals(userInfo)));
    }

    public void checkDeleteRight(LoginAccount user, Lecture lecture) {
        rightCheck(lecture.getHostUser().getId().equals(user.getId()));
    }
}
