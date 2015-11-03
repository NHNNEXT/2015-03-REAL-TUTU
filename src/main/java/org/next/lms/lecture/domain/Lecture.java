package org.next.lms.lecture.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"hostUser", "managers", "enrolledStudent", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "managers", "enrolledStudent", "contents"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private UserInfo hostUser;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();

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

    @Temporal(TemporalType.DATE)
    @Column(name = "START")
    private Date start;

    @Temporal(TemporalType.DATE)
    @Column(name = "END")
    private Date end;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private Integer majorType;

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

   public void addLessons(List<Lesson> lessons) {
       lessons.forEach(item->{
           item.setLecture(this);
           this.lessons.add(item);
       });
    }
}


