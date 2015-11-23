package org.next.lms.external;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "EXTERNAL")
public class External {

    @Id
    @Column(name = "EXTERNAL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "TITLE")
    private String title;

    @Column(name = "BODY")
    private String body;

}
