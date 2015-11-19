package org.next.lms.lecture;

import lombok.*;
import org.next.lms.lecture.Lecture;
import org.next.lms.lecture.auth.UserGroupCanReadContent;
import org.next.lms.lecture.auth.UserGroupCanWriteContent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "lecture", "defaultGroup", "name"})
@Entity
@Table(name = "USER_GROUP")
public class UserGroup {

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> userEnrolledLectures = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DEFAULT_GROUP", nullable = false, columnDefinition = "boolean default false")
    private Boolean defaultGroup = false;

    @Column(name = "NAME")
    private String name;


    public void update(UserGroup userGroup) {
        if(userGroup.defaultGroup != null)
            this.defaultGroup = userGroup.defaultGroup;
        if(userGroup.name != null)
            this.name = userGroup.name;
    }
}
