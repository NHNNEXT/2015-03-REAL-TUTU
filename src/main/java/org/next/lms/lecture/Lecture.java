package org.next.lms.lecture;

import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.content.ContentType;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;
import org.next.lms.lecture.repository.ContentTypeRepository;
import org.next.lms.lecture.repository.UserGroupCanReadContentRepository;
import org.next.lms.lecture.repository.UserGroupCanWriteContentRepository;
import org.next.lms.lecture.repository.UserGroupRepository;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.user.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"hostUser", "userGroups", "contentTypes", "userLikesLectures", "userEnrolledLectures", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "userGroups", "contentTypes", "userLikesLectures", "userEnrolledLectures", "contents", "name", "majorType", "registerPolicy"})
@Entity
@Table(name = "LECTURE")
public class Lecture {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private User hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserLikesLecture> userLikesLectures = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ContentType> contentTypes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> userEnrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "강의명을 입력해주세요.")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "전공 분류를 선택해주세요.")
    @Column(name = "MAJOR_TYPE")
    private Integer majorType;

    @NotNull(message = "가입 정책을 선택해주세요.")
    @Column(name = "REGISTER_POLICY")
    private Integer registerPolicy;


    // TODO 삭제된 상태라고 보기 어려운것 같다
    public void setDeleteState() {
        this.hostUser = null;
        this.userEnrolledLectures = null;
    }

    public void update(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository, UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository) {
        if (lecture.name != null)
            this.name = lecture.name;
        if (lecture.majorType != null)
            this.majorType = lecture.majorType;
        if (lecture.registerPolicy != null)
            this.registerPolicy = lecture.registerPolicy;
        if (lecture.writable != null)
            this.writable = lecture.writable;
        if (lecture.readable != null)
            this.readable = lecture.readable;
        removeDeleted(lecture, userGroupRepository, contentTypeRepository, userGroupCanReadContentRepository, userGroupCanWriteContentRepository);
        updateAndAddNew(lecture, userGroupRepository, contentTypeRepository);
    }

    private void updateAndAddNew(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository) {
        this.userGroups.removeIf(userGroup -> !lecture.getUserGroups().contains(userGroup));
        lecture.getUserGroups().forEach(updatedUserGroup -> {
            if (this.userGroups.contains(updatedUserGroup)) {
                this.userGroups.stream().filter(updatedUserGroup::equals).findFirst().get().update(updatedUserGroup);
            } else {
                updatedUserGroup.setLecture(this);
                this.userGroups.add(updatedUserGroup);
            }
        });
        this.contentTypes.removeIf(contentType -> !lecture.getContentTypes().contains(contentType));
        lecture.getContentTypes().forEach(updatedContentType -> {
            if (this.contentTypes.contains(updatedContentType)) {
                this.contentTypes.stream().filter(updatedContentType::equals).findFirst().get().update(updatedContentType);
            } else {
                updatedContentType.setLecture(this);
                this.contentTypes.add(updatedContentType);
            }
        });
    }

    @Transactional
    private void removeDeleted(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository, UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository) {
        List<UserGroup> deletedUserGroups = this.userGroups.stream().filter(userGroup -> !lecture.getUserGroups().contains(userGroup)).collect(Collectors.toList());
        deletedUserGroups.forEach(deletedUserGroup -> {
            userGroupCanReadContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
            userGroupCanWriteContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
        });
        this.userGroups.removeAll(deletedUserGroups);
        userGroupRepository.delete(deletedUserGroups);
        List<ContentType> deletedContentTypes = this.contentTypes.stream().filter(contentType -> !lecture.getContentTypes().contains(contentType)).collect(Collectors.toList());
        deletedContentTypes.forEach(deletedContentType -> {
            userGroupCanReadContentRepository.deleteByContentTypeId(deletedContentType.getId());
            userGroupCanWriteContentRepository.deleteByContentTypeId(deletedContentType.getId());
        });
        this.contentTypes.removeAll(deletedContentTypes);
        contentTypeRepository.delete(deletedContentTypes);
    }


    @Transient
    private List<List<Boolean>> writable;

    @Transient
    private List<List<Boolean>> readable;

    // TODO 정체가 뭘까?
    public void setAuthorities(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository) {
        userGroups.forEach(userGroup -> {
            userGroup.setLecture(this);
            userGroupCanReadContentRepository.deleteByUserGroupId(userGroup.getId());
            userGroupCanWriteContentRepository.deleteByUserGroupId(userGroup.getId());
        });
        contentTypes.forEach(contentType -> {
            contentType.setLecture(this);
            userGroupCanReadContentRepository.deleteByContentTypeId(contentType.getId());
            userGroupCanWriteContentRepository.deleteByContentTypeId(contentType.getId());
        });
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


