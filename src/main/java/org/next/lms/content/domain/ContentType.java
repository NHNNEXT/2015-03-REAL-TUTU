package org.next.lms.content.domain;

import lombok.*;
import org.next.lms.content.control.auth.UserGroupCanReadTodo;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanWriteContent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture", "todoReadable", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "todoReadable", "contents", "lecture", "endTime", "startTime", "todo", "todoOpen", "reply", "name"})
@Entity
@Table(name = "CONTENT_TYPE")
public class ContentType {

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanReadTodo> todoReadable = new ArrayList<>();

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

    @Column(name = "TODO", nullable = false, columnDefinition = "boolean default false")
    private Boolean todo = false;

    @Column(name = "TODO_OPEN", nullable = false, columnDefinition = "boolean default false")
    private Boolean todoOpen = false;

    @Column(name = "REPLY", nullable = false, columnDefinition = "boolean default false")
    private Boolean reply = false;

    @NotNull(message = "게시물 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(ContentType contentType) {
        if (contentType.endTime != null)
            this.endTime = contentType.endTime;
        if (contentType.startTime != null)
            this.startTime = contentType.startTime;
        if (contentType.todo != null) {
            this.todo = contentType.todo;
            if (contentType.todo)
                this.endTime = true;
        }
        if (contentType.todoOpen != null)
            this.todoOpen = contentType.todoOpen;
        if (contentType.reply != null)
            this.reply = contentType.reply;
        if (contentType.name != null)
            this.name = contentType.name;
    }
}
