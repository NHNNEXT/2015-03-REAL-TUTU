package org.next.lms.tag;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TAG")
public class Tag {


    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;




}
