package org.next.lms.lecture;

import lombok.*;
import org.next.lms.lecture.right.ContentType;
import org.next.lms.lecture.right.UserGroup;
import org.next.lms.user.User;
import org.next.lms.content.Content;
import org.next.infra.relation.UserEnrolledLecture;
import org.next.infra.relation.UserLikesLecture;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString(exclude = {"hostUser", "userGroups", "contentTypes", "likes", "userEnrolledLectures", "contents"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "userGroups", "contentTypes", "likes", "userEnrolledLectures", "contents"})
@Entity
@Table(name = "LECTURE")
public class Lecture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private User hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserLikesLecture> likes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ContentType> contentTypes = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> userEnrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAJOR_TYPE")
    private Integer majorType;

    @Column(name = "REGISTER_POLICY_TYPE")
    private Integer registerPolicyType;


    public void setDeleteState() {
        this.hostUser = null;
        this.userEnrolledLectures = null;
    }

    public void update(Lecture lecture) {
        if (lecture.name != null)
            this.name = lecture.name;
        if (lecture.majorType != null)
            this.majorType = lecture.majorType;
        if (lecture.registerPolicyType != null)
            this.registerPolicyType = lecture.registerPolicyType;
        if (lecture.registerPolicyType != null)
            this.registerPolicyType = lecture.registerPolicyType;
        if (lecture.userGroups != null)
            this.userGroups = lecture.userGroups;
        if (lecture.registerPolicyType != null)
            this.contentTypes = lecture.contentTypes;
    }
}


