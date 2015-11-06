package org.next.lms.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.lecture.domain.Lecture;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class LectureAuthority {
    public boolean hasAuthority(Lecture lecture, UserInfo userInfo) {
        return lecture.getHostUser().equals(userInfo) || lecture.getManagers().contains(userInfo) || lecture.getEnrolledStudent().contains(userInfo);
    }

    public boolean updateRight(UserInfo userInfo, Lecture one) {
        return one.getHostUser().getId().equals(userInfo.getId());
    }
}
