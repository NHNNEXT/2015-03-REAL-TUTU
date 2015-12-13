package org.next.lms.lecture.domain;

import lombok.*;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.infra.repository.TermRepository;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.ContentGroup;
import org.next.lms.like.domain.UserLikesLecture;
import org.next.lms.term.Term;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"hostUser", "userGroups", "contentGroups", "userLikesLectures", "userEnrolledLectures", "contents", "lectureNodeHasLectureList"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hostUser", "userGroups", "contentGroups", "userLikesLectures", "userEnrolledLectures", "contents", "name", "majorType", "registerPolicy", "lectureNodeHasLectureList"})
@Entity
@Table(name = "LECTURE")
public class Lecture implements ObjectOwnerKnowable{

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "TERM_ID")
//    private Term term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_USER_ID")
    private User hostUser;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserLikesLecture> userLikesLectures = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ContentGroup> contentGroups = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<UserEnrolledLecture> userEnrolledLectures = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<LectureNodeHasLecture> lectureNodeHasLectureList;



    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Content> contents = new ArrayList<>();

    @Id
    @Column(name = "LECTURE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "강의명을 입력해주세요.")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "전공 분류를 선택해주세요.")
    @Column(name = "MAJOR_TYPE")
    private Integer majorType;

    @NotNull(message = "가입 정책을 선택해주세요.")
    @Column(name = "REGISTER_POLICY")
    private Integer registerPolicy;


    // TODO 삭제된 상태라고 보기 어려운것 같다
    public void setDeleteState() {
        this.hostUser = null;
        this.userEnrolledLectures = null;
    }

    public void update(Lecture lecture) {
        if (lecture.name != null)
            this.name = lecture.name;
        if (lecture.majorType != null)
            this.majorType = lecture.majorType;
        if (lecture.registerPolicy != null)
            this.registerPolicy = lecture.registerPolicy;
        if (lecture.writable != null)
            this.writable = lecture.writable;
        if (lecture.readable != null)
            this.readable = lecture.readable;
        if (lecture.submitReadable != null)
            this.submitReadable = lecture.submitReadable;
    }


    @Transient
    private List<List<Boolean>> writable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    @Transient
    private List<List<Boolean>> readable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    @Transient
    private List<List<Boolean>> submitReadable;   // 클라이언트에서 데이터가 넘어와서 초기화 됨

    @Transient
    private Long termId;

//    public void setTerm(TermRepository termRepository) {
//        if (this.termId == null) {
//            this.term = null;
//            return;
//        }
//        this.term = termRepository.findOne(termId);
//    }

    @Override
    public User ownerOfObject() {
        return this.hostUser;
    }
}


