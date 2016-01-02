package org.next.lms.lecture.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"lecture", "lectureNode"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lecture", "lectureNode"})
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "LECTURE_NODE_HAS_LECTURE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LECTURE_ID", "LECTURE_NODE_ID"})
})
public class LectureNodeHasLecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_NODE_ID")
    private LectureNode lectureNode;

    @Id
    @Column(name = "LECTURE_NODE_HAS_LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
