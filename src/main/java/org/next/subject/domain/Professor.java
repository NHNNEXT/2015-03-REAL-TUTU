package org.next.subject.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROFESSOR")
public class Professor {

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfessorHaveLecture> professorHaveLectures= new ArrayList<>();

    @Id
    @Column(name = "PROFESSOR_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
