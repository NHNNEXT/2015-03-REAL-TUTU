package org.next.lms.content.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.config.AppConfig;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.infra.uploadfile.UploadedFile;
import org.next.infra.uploadfile.service.DomainHasAttachment;
import org.next.lms.lecture.domain.Lecture;
import org.next.lms.like.domain.UserLikesContent;
import org.next.lms.reply.domain.Reply;
import org.next.lms.submit.domain.UserHaveToSubmit;
import org.next.lms.tag.domain.Tag;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Setter
@ToString(exclude = {"attachments", "userLikesContents", "userHaveToSubmits", "tags", "linkedContents", "linkContents", "replies", "writer", "lecture", "contentGroup"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"attachments", "userLikesContents", "userHaveToSubmits", "tags", "linkedContents", "linkContents", "replies", "writer", "lecture", "contentGroup", "title", "hits", "body", "writeDate", "startTime", "endTime"})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "CONTENT")
public class Content implements ObjectOwnerKnowable, DomainHasAttachment {

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLikesContent> userLikesContents = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Size(min = 0, max = AppConfig.CONTENT_ATTACHMENTS_MAX_SIZE, message = "첨부파일은 최대 " + AppConfig.CONTENT_ATTACHMENTS_MAX_SIZE + "개 첨부할 수 있습니다.")
    // 인서트할때는 안막고, 불러갈때만 에러발생시킴..
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

    @Override
    public Integer getMaxAttachmentSize() {
        return AppConfig.CONTENT_ATTACHMENTS_MAX_SIZE;
    }

    @Override
    public Consumer<? super UploadedFile> getAttachmentAddAction() {
        return uploadedFile -> {
            uploadedFile.setContent(this);
        };
    }

    @Override
    public Consumer<? super UploadedFile> getAttachmentRemoveAction() {
        return uploadedFile -> {
            uploadedFile.setContent(null);
        };
    }

    public boolean userHaveToSubmit(Long userId) {
        return this.userHaveToSubmits.stream().filter(userHaveToSubmit -> userHaveToSubmit.getUser().getId().equals(userId)).findAny().isPresent();
    }

    public boolean userHaveToSubmit(User user) {
        return this.userHaveToSubmits.stream().filter(userHaveToSubmit -> userHaveToSubmit.getUser().equals(user)).findAny().isPresent();
    }


    public boolean isSchedule() {
        return ContentType.SCHEDULE.equals(contentGroup.getContentType());
    }

    public boolean isNotice() {
        return ContentType.NOTICE.equals(contentGroup.getContentType());
    }

    public boolean isSubmit() {
        return ContentType.SUBMIT.equals(contentGroup.getContentType());
    }

    public boolean isGeneral() {
        return ContentType.GENERAL.equals(contentGroup.getContentType());
    }
}

