package org.next.lms.term;

import lombok.*;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TERM")
public class Term {

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY)
    private List<Lecture> lectures = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END")
    private Date end;

}
