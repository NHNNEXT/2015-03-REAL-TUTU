//package org.next.lms.common.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//import org.next.lms.lecture.domain.Lecture;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Setter
//@ToString(exclude = {"lectures"})
//@NoArgsConstructor
//@EqualsAndHashCode(exclude = {"lectures"})
//@Entity
//@Table(name = "TERM")
//public class Term {
//
//
//    @OneToMany(mappedBy = "term", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private List<Lecture> lectures = new ArrayList<>();
//
//    @Id
//    @Column(name = "TERM_ID")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "NAME")
//    private String name;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "START")
//    private Date start;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "END")
//    private Date end;
//}
