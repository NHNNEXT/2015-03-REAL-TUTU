package org.next.subject.domain;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT_ATTEND_LECTURE")
public class StudentAttendLecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    // Field
    @Id
    @Column(name = "USER_AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
