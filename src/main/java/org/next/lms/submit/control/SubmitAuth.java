package org.next.lms.submit.control;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.submit.domain.UserHaveToSubmit;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class SubmitAuth extends CRUDBasicAuthCheck {
    public void checkSubmitReadable(UserHaveToSubmit userHaveToSubmit, User user) {
        boolean readable = userHaveToSubmit.getContent().getContentGroup().userCanReadSubmit(user);
        boolean host = userHaveToSubmit.getContent().getLecture().isHostUser(user);

        super.rightCheck(isObjectOwner(userHaveToSubmit, user) || host || readable);
    }
}
