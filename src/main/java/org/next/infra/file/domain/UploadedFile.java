package org.next.infra.file.domain;

import lombok.*;
import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.lms.content.domain.Content;
import org.next.lms.content.domain.UserEnrolledLecture;
import org.next.lms.content.domain.UserManageLecture;
import org.next.lms.lecture.domain.Lesson;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private LoginAccount uploadUser;

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


