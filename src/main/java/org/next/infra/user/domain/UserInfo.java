package org.next.infra.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.next.lms.lecture.domain.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"loginAccount", "enrolledLectures", "manageLectures", "hostLectures"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"loginAccount", "enrolledLectures", "manageLectures", "hostLectures"})
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    // Relation
    @JsonIgnore
    @OneToOne(mappedBy = "userInfo")
    private LoginAccount loginAccount;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserManageLecture> manageLectures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "hostUser", fetch = FetchType.LAZY)
    private List<Lecture> hostLectures = new ArrayList<>();

    // Field
    @Id
    @Column(name = "USER_INFO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(name = "USER_NAME")
    private String name;

    @Setter(AccessLevel.NONE)
    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Setter(AccessLevel.NONE)
    @Column(name = "STUDENT_ID")
    private String studentId;

    @Setter(AccessLevel.NONE)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    @Column(name = "MAJOR")
    private String major;

    public void setName(String name) {
        if(name == null) return;
        this.name = name;
    }

    public void setProfileUrl(String profileUrl) {
        if(profileUrl == null) return;
        this.profileUrl = profileUrl;
    }

    public void setStudentId(String studentId) {
        if(studentId == null) return;
        this.studentId = studentId;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) return;
        this.phoneNumber = phoneNumber;
    }

    public void setMajor(String major) {
        if(major == null) return;
        this.major = major;
    }
}
