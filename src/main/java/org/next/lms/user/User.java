package org.next.lms.user;

import lombok.*;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.infra.relation.UserInMenuLecture;
import org.next.lms.content.Content;
import org.next.lms.lecture.Lecture;
import org.next.infra.relation.UserLikesContent;
import org.next.infra.relation.UserLikesLecture;
import org.next.infra.relation.UserLikesReply;
import org.next.lms.message.Message;
import org.next.lms.reply.Reply;
import org.next.lms.user.state.AccountState;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"messages", "contents", "replies", "enrolledLectures", "inMenuLectures", "hostLectures", "likeLectures", "likeContents", "likeLessons", "likeReplies"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"messages", "contents", "replies", "enrolledLectures", "inMenuLectures", "hostLectures", "likeLectures", "likeContents", "likeLessons", "likeReplies"})
@Entity
@Table(name = "USER")
public class User {

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "hostUser", fetch = FetchType.LAZY)
    private List<Lecture> hostLectures = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserInMenuLecture> inMenuLectures = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserLikesLecture> likeLectures = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserLikesContent> likeContents = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserLikesReply> likeReplies = new ArrayList<>();

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "User Email Must Not Be Null")
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "User Password Must Not Be Null")
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "STATE")
    private AccountState state;

    @Column(name = "MENU")
    private String menu;

    @Lob
    @Column(name = "INTRODUCE")
    private String introduce;

    public void update(User passed) {
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
        if (passed.menu != null)
            this.menu = passed.menu;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        if (this.password == null) return;
        this.password = passwordEncoder.encode(this.password);

    }

    public boolean isPasswordCorrect(PasswordEncoder passwordEncoder, String password) {
        return this.password != null && passwordEncoder.matches(this.password, password);
    }

}
