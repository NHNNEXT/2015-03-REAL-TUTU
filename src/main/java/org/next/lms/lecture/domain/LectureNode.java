package org.next.lms.lecture.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "LECTURE_NODE")
public class LectureNode {

    @OneToMany(mappedBy = "lectureNode", fetch = FetchType.LAZY)
    private List<LectureNodeHasLecture> lectureNodeHasLectureList;

    @Id
    @Column(name = "LECTURE_NODE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @NotNull(message = "분류명을 입력해주세요.")
    @Column(name = "NAME")
    private String name;

}
