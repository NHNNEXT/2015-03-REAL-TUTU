package org.next.lms.term;

import lombok.*;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TERM", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"NAME"})
})
public class Term {

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY)
    private List<Lecture> lectures = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "학기명을 입력해주세요.")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "시작 날짜를 입력해주세요.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START")
    private Date start;

    @NotNull(message = "끝 날짜를 입력해주세요.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END")
    private Date end;

    @Column(name = "WRITER_ID")
    private Long writerId;

}
