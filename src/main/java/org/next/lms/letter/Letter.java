package org.next.lms.letter;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.lms.message.domain.MessageType;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Letter")
public class Letter {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @Id
    @Column(name = "LETTER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "LETTER")
    private String message;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "CHECKED")
    private boolean checked = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date = new Date();

    public void read() {
        this.checked = true;
    }
}
