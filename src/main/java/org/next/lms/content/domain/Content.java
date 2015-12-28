package org.next.lms.content.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.infra.uploadfile.UploadedFile;
import org.next.lms.content.relative.ContentLinkContent;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.like.domain.UserLikesContent;
import org.next.lms.reply.domain.Reply;
import org.next.lms.submit.UserHaveToSubmit;
import org.next.lms.tag.domain.Tag;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"attachments", "userLikesContents", "userHaveToSubmits", "tags", "linkedContents", "linkContents", "replies", "writer", "lecture", "contentGroup"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"attachments", "userLikesContents", "userHaveToSubmits", "tags", "linkedContents", "linkContents", "replies", "writer", "lecture", "contentGroup", "title", "hits", "body", "writeDate", "startTime", "endTime", "submitCanAttach"})
@Entity
@Table(name = "CONTENT")
public class Content implements ObjectOwnerKnowable{

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLikesContent> userLikesContents = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UploadedFile> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserHaveToSubmit> userHaveToSubmits = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "linkedContent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContentLinkContent> linkedContents = new ArrayList<>();

    @OneToMany(mappedBy = "linkContent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContentLinkContent> linkContents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_GROUP_ID")
    private ContentGroup contentGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @Id
    @Column(name = "CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "제목을 입력해주세요.")
    @Column(name = "TITLE")
    private String title;

    @Column(name = "HITS")
    private Long hits = 0L;

    @NotNull(message = "내용을 입력해주세요.")
    @Lob
    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME")
    private Date endTime;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name="SUBMIT_CAN_ATTACH")
    private boolean submitCanAttach = false;


    public void addReadCount() {
        this.hits++;
    }

    public void validate() {
        this.contentGroup.getContentType().validate(this);
    }

    @Override
    public User ownerOfObject() {
        return this.writer;
    }


}

