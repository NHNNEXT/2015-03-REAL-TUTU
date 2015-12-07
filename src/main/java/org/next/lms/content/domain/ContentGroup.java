package org.next.lms.content.domain;

import lombok.*;
import org.next.lms.content.control.auth.UserGroupCanReadSubmit;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture", "submitReadable", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "submitReadable", "contents", "lecture", "contentType", "submitOpen", "reply", "name", "attachment"})
@Entity
@Table(name = "CONTENT_GROUP")
public class ContentGroup {

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadSubmit> submitReadable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
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

    @Column(name = "SUBMIT_OPEN", nullable = false, columnDefinition = "boolean default false")
    private Boolean submitOpen = false;

    @Column(name = "REPLY", nullable = false, columnDefinition = "boolean default false")
    private Boolean reply = false;

    @Column(name = "ATTACHMENT", nullable = false, columnDefinition = "boolean default false")
    private Boolean attachment = false;

    @NotNull(message = "게시물 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(ContentGroup contentGroup) {
        if (contentGroup.submitOpen != null)
            this.submitOpen = contentGroup.submitOpen;
        if (contentGroup.contentType != null)
            this.contentType = contentGroup.contentType;
        if (contentGroup.reply != null)
            this.reply = contentGroup.reply;
        if (contentGroup.attachment != null)
            this.attachment = contentGroup.attachment;
        if (contentGroup.name != null)
            this.name = contentGroup.name;
    }


}
