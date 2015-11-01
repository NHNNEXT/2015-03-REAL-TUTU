package org.next.lms.lecture.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.next.infra.common.domain.MajorType;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.common.domain.Term;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"term", "hostUser", "managers", "enrolledStudent", "contents"})
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(exclude = {"term", "hostUser", "managers", "enrolledStudent", "contents"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_ID")
    private Term term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private UserInfo hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserManageLecture> managers = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> enrolledStudent = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private MajorType majorType;

    @Column(name = "PLAY_TIME")
    private String playTime;

    public void addManagers(List<UserInfo> managers) {
        managers.stream().forEach(managerUserInfo -> {
            UserManageLecture lecture = new UserManageLecture();
            lecture.setLecture(this);
            lecture.setUserInfo(managerUserInfo);
            this.managers.add(lecture);
        });
    }
}


