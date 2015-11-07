package org.next.lms.lecture.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.next.lms.like.UserLikesLecture;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString(exclude = {"hostUser", "managers", "enrolledStudent", "contents", "likes", "lessons"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "managers", "enrolledStudent", "contents", "likes", "lessons"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private UserInfo hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserLikesLecture> likes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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
    private Integer majorType;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    public void addManagers(List<UserInfo> managers) {
        managers.forEach(managerUserInfo -> {
            UserManageLecture relation = new UserManageLecture();
            relation.setLecture(this);
            relation.setUserInfo(managerUserInfo);
            this.managers.add(relation);
        });
    }

    public void addLessons(List<Lesson> lessons) {
        lessons.forEach(item -> {
            item.setLecture(this);
            this.lessons.add(item);
        });
    }

    public void setDeleteState() {
        this.hostUser = null;
        this.managers = null;
        this.enrolledStudent = null;
    }

    public void update(Lecture lecture) {
        if (lecture.name != null)
            this.name = lecture.name;
        if (lecture.majorType != null)
            this.majorType = lecture.majorType;
        if (lecture.date != null)
            this.date = lecture.date;
    }
}


