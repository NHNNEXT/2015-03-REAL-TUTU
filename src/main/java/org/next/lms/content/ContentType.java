package org.next.lms.content;

import lombok.*;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "lecture", "endTime", "startTime", "extendWrite", "onlyWriter", "statistic", "name"})
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

    @Column(name = "END_TIME")
    private Boolean endTime;

    @Column(name = "START_TIME")
    private Boolean startTime;

    @Column(name = "EXTEND_WRITE")
    private Boolean extendWrite;

    @Column(name = "ONLY_WRITER")
    private Boolean onlyWriter;

    @Column(name = "STATISTIC")
    private Boolean statistic;

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
