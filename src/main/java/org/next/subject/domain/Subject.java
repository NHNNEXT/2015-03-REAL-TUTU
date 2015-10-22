package org.next.subject.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class Subject {

    // Relation
    @OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Lecture> lectures = new ArrayList<>();

    // Field
    @Id
    @Column(name = "SUBJECT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;
}
