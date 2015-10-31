package org.next.infra.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserHostLecture;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserHostLecture> hostLectures = new ArrayList<>();

    // Field
    @Id
    @Column(name = "USER_INFO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MAJOR")
    private String major;
}
