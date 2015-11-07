package org.next.lms.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Reply;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ReplyAuthority extends Authority {
    public void checkUpdateRight(Reply fromDB, UserInfo userInfo) {
        rightCheck(fromDB.getWriter().getUserInfo().getId().equals(userInfo.getId()));
    }

    public void checkDeleteRight(Reply fromDB, UserInfo userInfo) {
        rightCheck(fromDB.getWriter().getUserInfo().getId().equals(userInfo.getId()));
    }
}
