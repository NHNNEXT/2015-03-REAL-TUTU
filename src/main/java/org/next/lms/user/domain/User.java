package org.next.lms.user.domain;

import lombok.*;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.lms.content.domain.Content;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.lecture.domain.UserEnrolledLecture;
import org.next.lms.like.domain.UserLikesContent;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.like.domain.UserLikesReply;
import org.next.lms.message.domain.Message;
import org.next.lms.reply.domain.Reply;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"messages", "contents", "replies", "hostLectures", "enrolledLectures", "likeLectures", "likeContents", "likeReplies", "email", "profileUrl", "state", "major", "introduce"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"messages", "contents", "replies", "hostLectures", "enrolledLectures", "likeLectures", "likeContents", "likeReplies", "email", "profileUrl", "state", "major", "introduce"})
@Entity
@Table(name = "USER")
public class User {

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
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

    @NotNull(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^.+@.+\\..+$", message = "이메일 형식이 맞지 않습니다.") // javascript: ^.+@.+\..+$
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "패스워드를 입력해주세요.")
    // 패스워드는 인크립트시 유효성 체크를 해야 하므로, 아래 메서드에서 체크
    @Column(name = "PASSWORD")
    private String password;

    @NotNull(message = "이름을 입력해주세요.")
    @Pattern(regexp = "\\S{2,10}", message = "이름은 2~10자입니다.")
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
        if (passed.email != null)
            this.email = passed.email;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        if (this.password == null) return;
        if (!this.password.matches("^\\S{6,20}$"))
            throw new PatternNotMatchedException("패스워드는 6~20자 입니다.");
        this.password = passwordEncoder.encode(this.password);

    }

    public boolean isPasswordCorrect(PasswordEncoder passwordEncoder, String password) {
        return this.password != null && passwordEncoder.matches(this.password, password);
    }

}
