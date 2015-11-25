package org.next.lms.content.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.content.Content;
import org.next.lms.content.ContentType;
import org.next.lms.user.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ContentAuth extends Auth {
    public void checkUpdateRight(Content fromDB, User user) {
        rightCheck(user.equals(fromDB.getWriter()));
    }

    public void checkDeleteRight(Content content, User user) {
        rightCheck(user.equals(content.getWriter()));
    }

    public void checkReadRight(Content content, User user) {
        rightCheck(content.getWriter().equals(user) || content.getLecture().getHostUser().equals(user) || isReadable(content, user));
    }

    public void checkWriteRight(ContentType contentType, User user) {
        rightCheck(contentType.getLecture().getHostUser().equals(user) || isWritable(contentType, user));
    }

    private boolean isReadable(Content content, User user) {
        return content.getType().getReadable().stream().filter(readable -> readable.getUserGroup().getUserEnrolledLectures().stream().filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }

    private boolean isWritable(ContentType contentType, User user) {
        return contentType.getWritable().stream().filter(writable -> writable.getUserGroup().getUserEnrolledLectures().stream().filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }


}
