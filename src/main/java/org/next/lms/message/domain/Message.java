package org.next.lms.message.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "MESSAGE")
// TODO Unique 논의 필요 -> Immutable Message 관련
//@Table(name = "Message", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"RECEIVER_ID", "TYPE", "PK_AT_BELONG_TYPE_TABLE"})
//})
public class Message {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private MessageType type;

    @Column(name = "PK_AT_BELONG_TYPE_TABLE")
    private Long pkAtBelongTypeTable;

    @Column(name = "URL")
    private String url;

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
