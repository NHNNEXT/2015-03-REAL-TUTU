package org.next.lms.content.domain;

import lombok.*;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.uploadfile.UploadedFile;
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
@ToString(exclude = {"attachment", "userLikesContents", "userHaveToSubmits", "replies", "writer", "lecture", "contentGroup"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"attachment", "userLikesContents", "userHaveToSubmits", "replies", "writer", "lecture", "contentGroup"})
@Entity
@Table(name = "CONTENT")
public class Content {

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<UserLikesContent> userLikesContents = new ArrayList<>();

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UploadedFile> attachment = new ArrayList<>();

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<UserHaveToSubmit> userHaveToSubmits = new ArrayList<>();

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TYPE_ID")
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


    public void update(Content content) {
        if (content.title != null)
            this.title = content.title;
        if (content.body != null)
            this.body = content.body;
        if (content.startTime != null)
            this.startTime = content.startTime;
        if (content.endTime != null)
            this.endTime = content.endTime;
    }

    public void addReadCount() {
        this.hits++;
    }

    // TODO 수정필요해보임 -> 관계만 끊는다고 삭제가 되는것은 아닌것 같다
    public void setDeleteState() {
        this.writer = null;
        this.lecture = null;
    }

    public void validate() {
        this.contentGroup.getContentType().validate(this);
    }
}

