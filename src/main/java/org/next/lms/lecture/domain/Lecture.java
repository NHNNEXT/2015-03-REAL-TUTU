package org.next.lms.lecture.domain;

import lombok.*;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserHostLecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"masterId", "enrolledStudent"})
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(exclude = {"masterId", "enrolledStudent"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserHostLecture> managers = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledStudent = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();
//
//    @Column(name = "")
//    private String ;
//
//    @Column(name = "")
//    private String ;
//
//    @Column(name = "")
//    private String ;
}


