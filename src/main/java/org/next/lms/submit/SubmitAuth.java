package org.next.lms.submit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class SubmitAuth extends Auth {
    public void checkUpdateRight(Submit fromDB, User user) {
        rightCheck(user.equals(fromDB.getWriter()));
    }

    public void checkDeleteRight(Submit fromDB, User user) {
        rightCheck(user.equals(fromDB.getWriter()));
    }
}
