package org.next.infra.uploadfile;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.next.lms.content.domain.Content;
import org.next.lms.submit.domain.Submit;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"uploadUser"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"uploadUser"})
@org.hibernate.annotations.Cache(region = "message", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "UPLOADED_FILE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"UGLY_FILE_NAME"})
})
public class UploadedFile {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBMIT_ID")
    private Submit submit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPLOAD_USER_ID")
    private User uploadUser;

    // Field
    @Id
    @Column(name = "UPLOADED_FILE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "UGLY_FILE_NAME")
    private String uglyFileName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime = new Date();
}


