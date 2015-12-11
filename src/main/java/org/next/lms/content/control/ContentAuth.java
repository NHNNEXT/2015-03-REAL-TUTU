package org.next.lms.content.control;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class ContentAuth extends CRUDBasicAuthCheck {

    public void checkReadRight(Content content, User user) {
        super.checkReadRight(content, user,
                content.getLecture().getHostUser().equals(user),
                isReadable(content, user)
        );
    }

    public void checkWriteRight(ContentGroup contentGroup, User user) {
        super.checkWriteRight(contentGroup.getLecture(), user,
                isWritable(contentGroup, user)
        );
    }

    private boolean isReadable(Content content, User user) {
        return content.getContentGroup().getReadable().stream()
                .filter(readable -> readable.getUserGroup().getUserEnrolledLectures().stream()
                        .filter(UserEnrolledLecture -> ApprovalState.OK.equals(UserEnrolledLecture.getApprovalState()))
                        .filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }

    private boolean isWritable(ContentGroup contentGroup, User user) {
        return contentGroup.getWritable().stream()
                .filter(writable -> writable.getUserGroup().getUserEnrolledLectures().stream()
                        .filter(userEnrolledLecture -> userEnrolledLecture.getUser().equals(user)).findFirst().isPresent()).findFirst().isPresent();
    }
}
