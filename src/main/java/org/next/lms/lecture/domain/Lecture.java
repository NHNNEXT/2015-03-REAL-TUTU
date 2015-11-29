package org.next.lms.lecture.domain;

import lombok.*;
import org.next.infra.repository.*;
import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentType;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.user.domain.User;

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

    public void update(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository, UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, ContentRepository contentRepository) {
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
        if (lecture.submitReadable != null)
            this.submitReadable = lecture.submitReadable;
        removeRelations(lecture, userGroupRepository, contentTypeRepository, userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository, contentRepository);
        updateAndAddNew(lecture, userGroupRepository, contentTypeRepository);
    }

    private void updateAndAddNew(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository) {
        lecture.getUserGroups().forEach(updatedUserGroup -> {
            if (this.userGroups.contains(updatedUserGroup)) {
                this.userGroups.stream().filter(updatedUserGroup::equals).findFirst().get().update(updatedUserGroup);
            } else {
                updatedUserGroup.setLecture(this);
                this.userGroups.add(updatedUserGroup);
            }
        });
        EnsureDefaultGroupExist();
        lecture.getContentTypes().forEach(updatedContentType -> {
            if (this.contentTypes.contains(updatedContentType)) {
                this.contentTypes.stream().filter(updatedContentType::equals).findFirst().get().update(updatedContentType);
            } else {
                updatedContentType.setLecture(this);
                this.contentTypes.add(updatedContentType);
            }
        });
    }

    private void EnsureDefaultGroupExist() {
        if(!this.userGroups.stream().filter(UserGroup::getDefaultGroup).findAny().isPresent()){
            this.userGroups.get(0).setDefaultGroup(true);
        }
    }

    private void removeRelations(Lecture lecture, UserGroupRepository userGroupRepository, ContentTypeRepository contentTypeRepository, UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, ContentRepository contentRepository) {
        List<UserGroup> deletedUserGroups = this.userGroups.stream().filter(userGroup -> !lecture.getUserGroups().contains(userGroup)).collect(Collectors.toList());
        List<ContentType> deletedContentTypes = this.contentTypes.stream().filter(contentType -> !lecture.getContentTypes().contains(contentType)).collect(Collectors.toList());
        deletedContentTypes.forEach(deletedContentType -> {
            RemoveRights(userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository, deletedContentType);
            RemoveAllContents(contentTypeRepository, contentRepository, deletedContentType); // TODO 로직변경
        });
        deletedUserGroups.forEach(deletedUserGroup -> {
            RemoveRights(userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository, deletedUserGroup);
        });
        this.userGroups.removeAll(deletedUserGroups);
        userGroupRepository.delete(deletedUserGroups);
        this.contentTypes.removeAll(deletedContentTypes);
        contentTypeRepository.delete(deletedContentTypes);
    }

    private void RemoveRights(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, UserGroup deletedUserGroup) {
        userGroupCanReadContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
        userGroupCanWriteContentRepository.deleteByUserGroupId(deletedUserGroup.getId());
        userGroupCanReadSubmitRepository.deleteByUserGroupId(deletedUserGroup.getId());
    }

    private void RemoveAllContents(ContentTypeRepository contentTypeRepository, ContentRepository contentRepository, ContentType deletedContentType) {
        ContentType type = contentTypeRepository.findOne(deletedContentType.getId());
        contentRepository.delete(type.getContents());
    }

    private void RemoveRights(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, ContentType deletedContentType) {
        userGroupCanReadContentRepository.deleteByContentTypeId(deletedContentType.getId());
        userGroupCanWriteContentRepository.deleteByContentTypeId(deletedContentType.getId());
        userGroupCanReadSubmitRepository.deleteByContentTypeId(deletedContentType.getId());
    }


    @Transient
    private List<List<Boolean>> writable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    @Transient
    private List<List<Boolean>> readable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    @Transient
    private List<List<Boolean>> submitReadable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    public void setAuthorities(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository) {
        userGroups.forEach(userGroup -> {
            userGroup.setLecture(this);
            userGroupCanReadContentRepository.deleteByUserGroupId(userGroup.getId());
            userGroupCanReadSubmitRepository.deleteByUserGroupId(userGroup.getId());
            userGroupCanWriteContentRepository.deleteByUserGroupId(userGroup.getId());
        });
        contentTypes.forEach(contentType -> {
            contentType.setLecture(this);
            userGroupCanReadContentRepository.deleteByContentTypeId(contentType.getId());
            userGroupCanReadSubmitRepository.deleteByContentTypeId(contentType.getId());
            userGroupCanWriteContentRepository.deleteByContentTypeId(contentType.getId());
        });

        createAuthTable(userGroupCanReadContentRepository, userGroupCanWriteContentRepository, userGroupCanReadSubmitRepository);
    }

    /*
        아래와 같이 생긴 모양의 권한 테이블을 생성하는 메서드

        Readable 권한
                        user group  |  user group
        content Type |      O               O
        content Type |      O               X


        Writable 권한
                        user group  |  user group
        content Type |      O               O
        content Type |      O               X

    */
    private void createAuthTable(UserGroupCanReadContentRepository userGroupCanReadContentRepository, UserGroupCanWriteContentRepository userGroupCanWriteContentRepository, UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository) {
        for (int userGrpIdx = 0; userGrpIdx < userGroups.size(); userGrpIdx++) {
            for (int contentTypIdx = 0; contentTypIdx < contentTypes.size(); contentTypIdx++) {

                if (userGrpIdx < writable.size() && contentTypIdx < writable.get(userGrpIdx).size())    // check table index range
                    if (writable.get(userGrpIdx).get(contentTypIdx))
                        makeWriteAuth(userGroupCanWriteContentRepository, userGroups.get(userGrpIdx), contentTypes.get(contentTypIdx));

                if (userGrpIdx < readable.size() && contentTypIdx < readable.get(userGrpIdx).size())    // check table index range
                    if (readable.get(userGrpIdx).get(contentTypIdx))
                        makeReadAuth(userGroupCanReadContentRepository, userGroups.get(userGrpIdx), contentTypes.get(contentTypIdx));

                if (userGrpIdx < submitReadable.size() && contentTypIdx < submitReadable.get(userGrpIdx).size())    // check table index range
                    if (submitReadable.get(userGrpIdx).get(contentTypIdx))
                        makeReadSubmitAuth(userGroupCanReadSubmitRepository, userGroups.get(userGrpIdx), contentTypes.get(contentTypIdx));
            }
        }
    }

    private void makeReadSubmitAuth(UserGroupCanReadSubmitRepository userGroupCanReadSubmitRepository, UserGroup userGroup, ContentType contentType) {
        UserGroupCanReadSubmit userGroupCanReadSubmit = new UserGroupCanReadSubmit();
        userGroupCanReadSubmit.setUserGroup(userGroup);
        userGroupCanReadSubmit.setContentType(contentType);
        userGroupCanReadSubmitRepository.save(userGroupCanReadSubmit);
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


