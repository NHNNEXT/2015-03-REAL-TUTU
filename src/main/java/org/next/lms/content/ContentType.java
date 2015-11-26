package org.next.lms.content;

import lombok.*;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "contents", "lecture", "endTime", "startTime", "extendWrite", "onlyWriter", "statistic", "name"})
@Entity
@Table(name = "CONTENT_TYPE")
public class ContentType {

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

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

    @Column(name = "EXTEND_WRITE", nullable = false, columnDefinition = "boolean default false")
    private Boolean extendWrite = false;

    @Column(name = "ONLY_WRITER", nullable = false, columnDefinition = "boolean default false")
    private Boolean onlyWriter = false;

    @Column(name = "STATISTIC", nullable = false, columnDefinition = "boolean default false")
    private Boolean statistic = false;

    @NotNull(message = "게시물 분류 이름을 입력해주세요.")
    @Column(name = "NAME")
    private String name;


    public void update(ContentType contentType) {
        if (contentType.endTime != null)
            this.endTime = contentType.endTime;
        if (contentType.startTime != null)
            this.startTime = contentType.startTime;
        if (contentType.extendWrite) {
            this.extendWrite = this.startTime && this.endTime;
        }
        if (contentType.onlyWriter != null)
            this.onlyWriter = contentType.onlyWriter;
        if (contentType.statistic != null)
            this.statistic = contentType.statistic;
        if (contentType.name != null)
            this.name = contentType.name;
    }
}
