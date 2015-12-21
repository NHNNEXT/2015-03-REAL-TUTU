package org.next.lms.reply.control;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.content.control.ContentAuth;
import org.next.lms.content.domain.Content;
import org.next.lms.reply.domain.Reply;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ReplyAuth extends CRUDBasicAuthCheck {

    @Autowired
    private ContentAuth contentAuth;

    public void checkWriteRight(Content content, User user) {
        // Content 를 읽을 수 있다면 댓글을 작성할 수 있다.
        contentAuth.checkReadRight(content, user);
    }

    public void checkReadRight(Reply reply, User user) {
        // Content 를 읽을 수 있다면 댓글을 읽을 수 있다.
        Content content = reply.getContent();
        contentAuth.checkReadRight(content, user);
    }
}
