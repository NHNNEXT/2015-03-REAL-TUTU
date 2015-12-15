package org.next.infra.mail;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "MAIL_AUTH")
public class MailAuth {

    @Id
    @Column(name = "MAIL_AUTH_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "EMAIL")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRED")
    private Date expiredTime = new Date();

    public MailAuth(String key, String email) {
        this.key = key;
        this.email = email;
    }
}
