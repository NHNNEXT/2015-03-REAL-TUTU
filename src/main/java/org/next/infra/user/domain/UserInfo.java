package org.next.infra.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.like.UserLikesContent;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.like.UserLikesLesson;
import org.next.lms.like.UserLikesReply;

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

    @OneToOne(mappedBy = "userInfo")
    private LoginAccount loginAccount;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserManageLecture> manageLectures = new ArrayList<>();

    @OneToMany(mappedBy = "hostUser", fetch = FetchType.LAZY)
    private List<Lecture> hostLectures = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserLikesLecture> likesLectures = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserLikesContent> likesContents = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserLikesLesson> likesLessons = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private List<UserLikesReply> likeReplies = new ArrayList<>();

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

    @Lob
    @Column(name = "INTRODUCE")
    private String introduce;


    public void update(UserInfo passed) {
        if (passed.name != null)
            this.name = passed.name;
        if (passed.profileUrl != null)
            this.profileUrl = passed.profileUrl;
        if (passed.studentId != null)
            this.studentId = passed.studentId;
        if (passed.phoneNumber != null)
            this.phoneNumber = passed.phoneNumber;
        if (passed.major != null)
            this.major = passed.major;
        if (passed.introduce != null)
            this.introduce = passed.introduce;
    }
}
