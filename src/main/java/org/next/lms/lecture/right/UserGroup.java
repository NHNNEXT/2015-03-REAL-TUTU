package org.next.lms.lecture.right;

import lombok.*;
import org.next.lms.lecture.Lecture;
import org.next.lms.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"writable", "readable", "lecture"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"writable", "readable", "lecture"})
@Entity
@Table(name = "USER_GROUP")
public class UserGroup {

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanWriteContent> writable = new ArrayList<>();

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
    private List<UserGroupCanReadContent> readable = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DEFAULT_GROUP")
    private Boolean defaultGroup;

    @Column(name = "NAME")
    private String name;


}
