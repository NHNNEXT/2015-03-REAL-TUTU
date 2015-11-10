package org.next.lms.lecture;

import lombok.*;
import org.next.infra.relation.UserLikesLesson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"likes", "lecture"})
@Entity
@Table(name = "LESSON")
public class Lesson {

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<UserLikesLesson> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "LESSON_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "START")
    private Date start;

    @Temporal(TemporalType.DATE)
    @Column(name = "END")
    private Date end;
}
