package org.next.infra.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "loginAccount")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"loginAccount"})
@Entity
@Table(name = "USER_INFO")
public class DetailUserInfo {

    // Relation
    @OneToOne(mappedBy = "detailUserInfo")
    private LoginAccount loginAccount;

    // Field
    @Id
    @Column(name = "DETAIL_USER_INFO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MAJOR")
    private String major;
}
