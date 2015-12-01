package org.next.lms.content.control;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.Auth;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.user.domain.User;
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

    public void checkWriteRight(ContentGroup contentGroup, User user) {
        rightCheck(contentGroup.getLecture().getHostUser().equals(user) || isWritable(contentGroup, user));
    }

    private boolean isReadable(Content content, User user) {
        return content.getContentGroup().getReadable().stream().filter(readable -> readable.getUserGroup().getUserEnrolledLectures().stream().filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }

    private boolean isWritable(ContentGroup contentGroup, User user) {
        return contentGroup.getWritable().stream().filter(writable -> writable.getUserGroup().getUserEnrolledLectures().stream().filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }


}
