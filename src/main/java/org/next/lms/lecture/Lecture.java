package org.next.lms.lecture;

import lombok.*;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;
import org.next.lms.lecture.repository.UserGroupCanReadContentRepository;
import org.next.lms.lecture.repository.UserGroupCanWriteContentRepository;
import org.next.lms.user.User;
import org.next.lms.content.Content;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.infra.relation.UserLikesLecture;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString(exclude = {"hostUser", "userGroups", "contentTypes", "likes", "users", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "userGroups", "contentTypes", "likes", "users", "contents"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private User hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserLikesLecture> likes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ContentType> contentTypes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> users = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAJOR_TYPE")
    private Integer majorType;

    @Column(name = "REGISTER_POLICY_TYPE")
    private Integer registerPolicyType;


    public void setDeleteState() {
        this.hostUser = null;
        this.users = null;
    }

    public void update(Lecture lecture) {
        if (lecture.name != null)
            this.name = lecture.name;
        if (lecture.majorType != null)
            this.majorType = lecture.majorType;
        if (lecture.registerPolicyType != null)
            this.registerPolicyType = lecture.registerPolicyType;
        if (lecture.registerPolicyType != null)
            this.registerPolicyType = lecture.registerPolicyType;
        if (lecture.userGroups != null)
            this.userGroups = lecture.userGroups;
        if (lecture.registerPolicyType != null)
            this.contentTypes = lecture.contentTypes;
    }

    @Transient
    private List<List<Boolean>> writable;

    @Transient
    private List<List<Boolean>> readable;

    public void setAuthorities(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository) {
        userGroups.forEach(userGroup -> userGroup.setLecture(this));
        contentTypes.forEach(userGroup -> userGroup.setLecture(this));

        for (int i = 0; i < userGroups.size(); i++) {
            for (int j = 0; j < contentTypes.size(); j++) {
                if (writable.size() > i && writable.get(i).size() > j)
                    if (writable.get(i).get(j))
                        makeWriteAuth(userGroupCanWriteContentRepository, userGroups.get(i), contentTypes.get(j));
                if (readable.size() > i && readable.get(i).size() > j)
                    if (readable.get(i).get(j))
                        makeReadAuth(userGroupCanReadContentRepository, userGroups.get(i), contentTypes.get(j));
            }
        }
    }

    private void makeReadAuth(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroup userGroup, ContentType contentType) {
        UserGroupCanReadContent userGroupCanReadContent = new UserGroupCanReadContent();
        userGroupCanReadContent.setUserGroup(userGroup);
        userGroupCanReadContent.setContentType(contentType);
        userGroupCanReadContentRepository.save(userGroupCanReadContent);
    }

    private void makeWriteAuth(UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroup userGroup, ContentType contentType) {
        UserGroupCanWriteContent userGroupCanWriteContent = new UserGroupCanWriteContent();
        userGroupCanWriteContent.setUserGroup(userGroup);
        userGroupCanWriteContent.setContentType(contentType);
        userGroupCanWriteContentRepository.save(userGroupCanWriteContent);
    }


}


