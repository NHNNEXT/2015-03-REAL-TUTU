package org.next.infra.mail;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
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

    @Column(name = "UUID_KEY")
    private String key;

    @Column(name = "EMAIL")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRED")
    private Date expiredTime = getFutureDate(+3);

    public MailAuth(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public Date getFutureDate(int amountOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, amountOfDays);
        return cal.getTime();
    }
}
