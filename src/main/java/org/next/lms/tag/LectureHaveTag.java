package org.next.lms.tag;

import lombok.*;
import org.next.lms.lecture.Lecture;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "LECTURE_HAVE_TAG")
public class LectureHaveTag {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

}
