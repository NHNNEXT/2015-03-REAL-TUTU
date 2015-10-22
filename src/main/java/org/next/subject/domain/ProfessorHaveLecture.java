package org.next.subject.domain;

import javax.persistence.*;

@Entity
@Table(name = "PROFESSOR_HAVE_LECTURE")
public class ProfessorHaveLecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFESSOR_ID")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    // Field
    @Id
    @Column(name = "USER_AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
