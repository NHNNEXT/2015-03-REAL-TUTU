package org.next.lms.submit;

import lombok.*;
import org.next.lms.content.domain.Content;
import org.next.lms.like.domain.UserLikesReply;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"content", "writer"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"content", "writer"})
@Entity
@Table(name = "SUBMIT")
public class Submit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Id
    @Column(name = "SUBMIT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "내용을 입력해주세요.")
    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    public void update(Submit submit) {
        this.writeDate = new Date();
        this.body = submit.body;
    }

    public void setDeleteState() {
        this.writer = null;
        this.content = null;
    }
}
