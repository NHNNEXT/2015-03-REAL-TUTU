package org.next.core.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "loginAccount")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"loginAccount"})
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    // Relation
    @OneToOne(mappedBy = "userInfo")
    private LoginAccount loginAccount;

    // Field
    @Id
    @Column(name = "USER_INFO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MAJOR")
    private String major;
}
