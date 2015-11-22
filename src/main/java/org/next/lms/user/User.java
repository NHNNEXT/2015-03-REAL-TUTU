package org.next.lms.user;

import lombok.*;
import org.next.lms.lecture.UserEnrolledLecture;
import org.next.lms.content.Content;
import org.next.lms.lecture.Lecture;
import org.next.lms.like.UserLikesContent;
import org.next.lms.like.UserLikesLecture;
import org.next.lms.like.UserLikesReply;
import org.next.lms.message.Message;
import org.next.lms.reply.Reply;
import org.next.lms.user.state.AccountState;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"messages", "contents", "replies", "hostLectures", "enrolledLectures", "likeLectures", "likeContents", "likeReplies", "email", "profileUrl", "state", "major", "introduce"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"messages", "contents", "replies", "hostLectures", "enrolledLectures", "likeLectures", "likeContents", "likeReplies", "email", "profileUrl", "state", "major", "introduce"})
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
    @Pattern(regexp = "^.+@.+\\..+$") // javascript: ^.+@.+\..+$
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "User Password Must Not Be Null")
    // 패스워드는 인크립트시 유효성 체크를 해야 하므로, 아래 메서드에서 체크
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "STATE")
    private AccountState state;

    @Lob
    @Column(name = "INTRODUCE")
    private String introduce;

    public void update(User passed) {
        if (passed.name != null)
            this.name = passed.name;
        if (passed.profileUrl != null)
            this.profileUrl = passed.profileUrl;
        if (passed.major != null)
            this.major = passed.major;
        if (passed.introduce != null)
            this.introduce = passed.introduce;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        if (this.password == null) return;
        if (!this.password.matches("^\\w{6,20}$")) {
            Set<ConstraintViolation<String>> errors = new HashSet<>();
            throw new ConstraintViolationException(errors);
        }
        this.password = passwordEncoder.encode(this.password);

    }

    public boolean isPasswordCorrect(PasswordEncoder passwordEncoder, String password) {
        return this.password != null && passwordEncoder.matches(this.password, password);
    }

}
