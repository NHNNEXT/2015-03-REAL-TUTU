package org.next.lms.lecture.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.lms.lecture.ContentType;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.UserGroup;
import org.next.lms.lecture.repository.UserGroupCanReadContentRepository;
import org.next.lms.lecture.repository.UserGroupCanWriteContentRepository;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LectureHasAuthInfo {
    private Lecture lecture;
    private List<List<Boolean>> write;
    private List<List<Boolean>> read;

    private UserGroupCanReadContentRepository userGroupCanReadContentRepository;
    private UserGroupCanWriteContentRepository userGroupCanWriteContentRepository;

    public void setAuthorities() {
        List<UserGroup> userGroups = lecture.getUserGroups();
        List<ContentType> contentTypes = lecture.getContentTypes();
        for (int i = 0; i < userGroups.size(); i++) {
            for (int j = 0; j < contentTypes.size(); j++) {
                if (write.size() >= i && write.get(i).size() >= j)
                    if (write.get(i).get(j))
                        makeWriteAuth(userGroups.get(i), contentTypes.get(j));
                if (read.size() >= i && read.get(i).size() >= j)
                    if (read.get(i).get(j))
                        makeReadAuth(userGroups.get(i), contentTypes.get(j));
            }
        }
    }

    private void makeReadAuth(UserGroup userGroup, ContentType contentType) {
        UserGroupCanReadContent userGroupCanReadContent = new UserGroupCanReadContent();
        userGroupCanReadContent.setUserGroup(userGroup);
        userGroupCanReadContent.setContentType(contentType);
        userGroupCanReadContentRepository.save(userGroupCanReadContent);
    }

    private void makeWriteAuth(UserGroup userGroup, ContentType contentType) {
        UserGroupCanWriteContent userGroupCanWriteContent = new UserGroupCanWriteContent();
        userGroupCanWriteContent.setUserGroup(userGroup);
        userGroupCanWriteContent.setContentType(contentType);
        userGroupCanWriteContentRepository.save(userGroupCanWriteContent);
    }
}
