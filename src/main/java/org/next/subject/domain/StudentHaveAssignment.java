package org.next.subject.domain;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT_HAVE_ASSIGNMENT")
public class StudentHaveAssignment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNMENT_ID")
    private Assignment assignment;

    // Field
    @Id
    @Column(name = "USER_AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
