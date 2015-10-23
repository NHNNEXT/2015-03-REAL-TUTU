package org.next.lecturemanager.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"studentHaveAssignments", "lecture"})
@Entity
@Table(name = "ASSIGNMENT")
public class Assignment {

    // Relation
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentHaveAssignment> studentHaveAssignments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    // Field
    @Id
    @GeneratedValue
    @Column(name = "ASSIGNMENT_ID")
    private Long id;

    @Column(name = "DUE_DATE")
    private Date dueDate;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

}
