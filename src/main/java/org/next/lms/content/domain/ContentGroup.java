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
@EqualsAndHashCode(exclude = {"writable", "readable", "submitReadable", "contents", "lecture", "endTime", "startTime", "submit", "submitOpen", "reply", "name"})
@Entity
@Table(name = "CONTENT_GROUP")
public class ContentGroup {

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadSubmit> submitReadable = new ArrayList<>();

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "END_TIME", nullable = false, columnDefinition = "boolean default false")
    private Boolean endTime = false;

    @Column(name = "START_TIME", nullable = false, columnDefinition = "boolean default false")
    private Boolean startTime = false;

    @Column(name = "SUBMIT", nullable = false, columnDefinition = "boolean default false")
    private Boolean submit = false;

    @Column(name = "SUBMIT_OPEN", nullable = false, columnDefinition = "boolean default false")
    private Boolean submitOpen = false;

    @Column(name = "REPLY", nullable = false, columnDefinition = "boolean default false")
    private Boolean reply = false;

    @NotNull(message = "게시물 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(ContentGroup contentGroup) {
        if (contentGroup.endTime != null)
            this.endTime = contentGroup.endTime;
        if (contentGroup.startTime != null)
            this.startTime = contentGroup.startTime;
        if (contentGroup.submit != null) {
            this.submit = contentGroup.submit;
            if (contentGroup.submit)
                this.endTime = true;
        }
        if (contentGroup.submitOpen != null)
            this.submitOpen = contentGroup.submitOpen;
        if (contentGroup.reply != null)
            this.reply = contentGroup.reply;
        if (contentGroup.name != null)
            this.name = contentGroup.name;
    }

}
