package org.next.lms.lecture.domain;

import lombok.*;
import org.next.infra.user.domain.UserInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"masterId", "enrolledStudent"})
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(exclude = {"masterId", "enrolledStudent"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "MASTER_ID")
    private UserInfo masterId;

    @OneToMany(mappedBy = "enrolledLecture", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserInfo> enrolledStudent = new ArrayList<>();
//
//    @Column(name = "")
//    private String ;
//
//    @Column(name = "")
//    private String ;
//
//    @Column(name = "")
//    private String ;
}


