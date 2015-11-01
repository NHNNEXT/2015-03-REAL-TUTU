package org.next.lms.content.domain;

import lombok.*;
import org.next.infra.user.domain.LoginAccount;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"content"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"content"})
@Entity
@Table(name = "Reply")
public class Reply {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CONTENT_ID")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private LoginAccount writer;

    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BODY")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WRITE_DATE")
    private Date writeDate;
}
