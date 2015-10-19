package org.next.core.user.domain;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private GenderType gender;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MAJOR")
    private String major;

    public LoginAccount getLoginAccount() {
        return loginAccount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GenderType getGender() {
        return gender;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMajor() {
        return major;
    }
}
