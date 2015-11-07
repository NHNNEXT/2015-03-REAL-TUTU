package org.next.lms.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ContentAuthority extends Authority {
    public void checkUpdateRight(Content fromDB, UserInfo userInfo) {
        rightCheck(fromDB.getWriter().getUserInfo().equals(userInfo));
    }

    public void checkDeleteRight(Content content, UserInfo userInfo) {
        rightCheck((content.getWriter().getUserInfo().equals(userInfo)));
    }

}
