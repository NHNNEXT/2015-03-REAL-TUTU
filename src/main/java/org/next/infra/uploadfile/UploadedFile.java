package org.next.infra.uploadfile;

import lombok.*;
import org.next.lms.user.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"uploadUser"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"uploadUser"})
@Entity
@Table(name = "UPLOADED_FILE")
public class UploadedFile {

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

