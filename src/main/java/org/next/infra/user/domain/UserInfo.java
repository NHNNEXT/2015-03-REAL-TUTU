package org.next.infra.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENROLLED_LECTURE_ID")
    private Lecture enrolledLecture;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "masterId")
    private Lecture lecture;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ENROLLED_STUDENT_USER_INFO_ID")
//    private UserInfo enrolledStudentUserInfo;
//
//    @OneToMany(mappedBy = "enrolledStudentUserInfo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private List<UserInfo> enrolledStudent = new ArrayList<>();

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
