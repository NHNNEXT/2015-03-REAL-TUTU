package org.next.lecturemanager.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"studentAttendLectures", "studentHaveAssignments"})
@Entity
@Table(name = "STUDENT")
public class Student {

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentAttendLecture> studentAttendLectures = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentHaveAssignment> studentHaveAssignments= new ArrayList<>();

    @Id
    @Column(name = "STUDENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
