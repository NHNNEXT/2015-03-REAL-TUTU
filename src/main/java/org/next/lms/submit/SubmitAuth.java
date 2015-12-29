package org.next.lms.submit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class SubmitAuth extends CRUDBasicAuthCheck {
    public void checkSubmitReadable(UserHaveToSubmit userHaveToSubmit, User user) {
        boolean readable = userHaveToSubmit.getContent().getContentGroup()
                .getSubmitReadable().stream()
                .filter(userGroupCanReadSubmit ->
                        userGroupCanReadSubmit.getUserGroup().getUserEnrolledLectures().stream()
                                .filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user))
                                .findAny().isPresent())
                .findAny().isPresent();

        boolean host = userHaveToSubmit.getContent().getLecture().getHostUser().equals(user);

        super.rightCheck(isObjectOwner(userHaveToSubmit, user) || host || readable);
    }
}
