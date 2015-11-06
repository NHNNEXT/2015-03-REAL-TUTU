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
public class ContentAuthority {
    public boolean updateRight(Content fromDB, UserInfo userInfo) {
        return fromDB.getWriter().getUserInfo().equals(userInfo);
    }

    public boolean deleteRight(Content content, UserInfo userInfo) {
        return content.getWriter().getUserInfo().equals(userInfo);
    }
}
