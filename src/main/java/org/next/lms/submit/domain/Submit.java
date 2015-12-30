package org.next.lms.submit.domain;

import lombok.*;
import org.next.config.AppConfig;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.infra.uploadfile.UploadedFile;
import org.next.infra.uploadfile.service.DomainHasAttachment;
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
@ToString(exclude = {"userHaveToSubmit", "writer"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userHaveToSubmit", "writer"})
@Entity
@Table(name = "SUBMIT")
public class Submit implements ObjectOwnerKnowable, DomainHasAttachment {

    @OneToMany(mappedBy = "submit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Size(min = 0, max = AppConfig.SUBMIT_ATTACHMENTS_MAX_SIZE, message = "첨부파일은 최대 " + AppConfig.SUBMIT_ATTACHMENTS_MAX_SIZE + "개 첨부할 수 있습니다.")
    // 인서트할때는 안막고, 불러갈때만 에러발생시킴..
    private List<UploadedFile> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_HAVE_TO_SUBMIT_ID")
    private UserHaveToSubmit userHaveToSubmit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "내용을 입력해주세요.")
    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Override
    public User ownerOfObject() {
        return this.writer;
    }

    @Override
    public Integer getMaxAttachmentSize() {
        return AppConfig.SUBMIT_ATTACHMENTS_MAX_SIZE;
    }

    @Override
    public Consumer<? super UploadedFile> getAttachmentAddAction() {
        return uploadedFile -> {
            uploadedFile.setSubmit(this);
        };
    }

    @Override
    public Consumer<? super UploadedFile> getAttachmentRemoveAction() {
        return  uploadedFile -> {
            uploadedFile.setSubmit(null);
        };
    }
}
