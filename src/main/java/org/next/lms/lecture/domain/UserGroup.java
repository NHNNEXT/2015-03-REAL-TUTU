package org.next.lms.lecture.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.infra.exception.unique.UniqueKeys;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.lecture.control.auth.ApprovalState;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "submitReadable", "userEnrolledLectures", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "submitReadable", "userEnrolledLectures", "lecture", "defaultGroup", "name"})

@Entity
@Table(name = "USER_GROUP", uniqueConstraints = {@UniqueConstraint(name = UniqueKeys.USER_GROUP_NAME_ALREADY_EXIST, columnNames = {"NAME", "LECTURE_ID"})})
public class UserGroup {

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanReadSubmit> submitReadable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> userEnrolledLectures = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "DEFAULT_GROUP")
    private boolean defaultGroup = false;

    @NotNull(message = "그룹 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(UserGroup userGroup) {
        this.defaultGroup = userGroup.defaultGroup;
        if (userGroup.name != null)
            this.name = userGroup.name;
    }

    public boolean isUserExist(User user) {
        return userEnrolledLectures.stream()
                .filter(userEnrolledLecture ->
                        ApprovalState.OK.equals(userEnrolledLecture.getApprovalState())
                                && userEnrolledLecture.getUser().equals(user)).findAny().isPresent();
    }

    public Boolean canRead(ContentGroup contentGroup) {
        return this.readable.stream().filter(relation -> relation.getContentGroup().equals(contentGroup)).findAny().isPresent();
    }

    public Boolean canWrite(ContentGroup contentGroup) {
        return this.writable.stream().filter(relation -> relation.getContentGroup().equals(contentGroup)).findAny().isPresent();
    }

    public Boolean canSubmitRead(ContentGroup contentGroup) {
        return this.submitReadable.stream().filter(relation -> relation.getContentGroup().equals(contentGroup)).findAny().isPresent();
    }

    public List<User> getUsers() {
        return userEnrolledLectures.stream().map(UserEnrolledLecture::getUser).collect(Collectors.toList());
    }
}
