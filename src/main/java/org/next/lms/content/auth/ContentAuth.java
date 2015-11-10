package org.next.lms.content.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.user.User;
import org.next.lms.content.Content;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ContentAuth extends Auth {
    public void checkUpdateRight(Content fromDB, User user) {
        rightCheck(fromDB.getWriter().equals(user));
    }

    public void checkDeleteRight(Content content, User user) {
        rightCheck((content.getWriter().equals(user)));
    }

}
