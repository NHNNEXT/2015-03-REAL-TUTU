package org.next.lms.lecture.right;

import lombok.*;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "lecture"})
@Entity
@Table(name = "CONTENT_TYPE")
public class ContentType {

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "contentType", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

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


}
