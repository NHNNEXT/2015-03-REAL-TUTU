package org.next.lms.reply.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.user.User;
import org.next.lms.reply.Reply;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ReplyAuth extends Auth {
    public void checkUpdateRight(Reply fromDB, User user) {
        rightCheck(fromDB.getWriter().getId().equals(user.getId()));
    }

    public void checkDeleteRight(Reply fromDB, User user) {
        rightCheck(fromDB.getWriter().getId().equals(user.getId()));
    }
}