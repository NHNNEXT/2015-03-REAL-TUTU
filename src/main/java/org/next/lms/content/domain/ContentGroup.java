package org.next.lms.content.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.infra.exception.unique.UniqueKeys;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture", "submitReadable", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "submitReadable", "contents", "lecture", "contentType", "submitOpen", "reply", "name"})
@Entity
@Table(name = "CONTENT_GROUP", uniqueConstraints = {@UniqueConstraint(name = UniqueKeys.CONTENT_GROUP_NAME_ALREADY_EXIST, columnNames = {"NAME", "LECTURE_ID"})})
public class ContentGroup {

    @OneToMany(mappedBy = "contentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroupCanReadSubmit> submitReadable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTENT_TYPE", nullable = false)
    private ContentType contentType = ContentType.GENERAL;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "SUBMIT_OPEN")
    private boolean submitOpen = false;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "REPLY")
    private boolean reply = false;

    @NotNull(message = "게시물 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(ContentGroup contentGroup) {
        this.submitOpen = contentGroup.submitOpen;
        this.reply = contentGroup.reply;
        if (contentGroup.name != null)
            this.name = contentGroup.name;
    }

    public boolean hasReadRight(User user) {
        return readable.stream()
                .filter(readable -> readable.getUserGroup().isUserExist(user)).findAny().isPresent();
    }

    public boolean hasWriteRight(User user) {
        return writable.stream()
                .filter(writable -> writable.getUserGroup().isUserExist(user)).findAny().isPresent();
    }
    public boolean hasSubmitReadRight(User user) {
        return submitReadable.stream()
                .filter(submitReadable -> submitReadable.getUserGroup().isUserExist(user)).findAny().isPresent();
    }

}
