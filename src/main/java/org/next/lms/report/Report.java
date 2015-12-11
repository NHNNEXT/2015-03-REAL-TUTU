package org.next.lms.report;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Report")
public class Report {

    @Id
    @Column(name = "REPORT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "내용을 입력해주세요.")
    @Column(name = "BODY")
    private String body;
}
