package org.next.lms.message;

import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.user.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Message")
public class Message {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Enumerated
    @Column(name = "TYPE")
    private MessageType type;

    @Column(name = "TYPE_ID")
    private Long typeId;

    @Column(name = "URL")
    private String url;

    @Column(name = "READ")
    private Boolean read;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;

}
