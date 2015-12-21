package org.next.infra.mail;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "MAIL_AUTH", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"UUID_KEY", "EMAIL"})
})
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
    private Date expiredTime;

    @Column(name = "PASSWORD_CHANGE_TRY_COUNT")
    private Integer passwordChangeTryCount = 0;

    public MailAuth(String key, String email, int expiredDays) {
        this.key = key;
        this.email = email;
        this.expiredTime = getFutureDate(expiredDays);
    }

    public Date getFutureDate(int amountOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, amountOfDays);
        return cal.getTime();
    }

    public boolean noMoreTryCount() {
        return this.passwordChangeTryCount <= 0;
    }

    public void minusTryCount() {
        this.passwordChangeTryCount--;
    }

    public void giveTryCount(int tryCount) {
        this.passwordChangeTryCount = tryCount;
    }
}
