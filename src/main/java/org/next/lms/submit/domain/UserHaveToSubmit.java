package org.next.lms.submit.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.lms.content.domain.Content;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"user", "content", "submits"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "content", "submits"})
@Entity
@Table(name = "USER_HAVE_TO_SUBMIT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "CONTENT_ID"})
})
public class UserHaveToSubmit implements ObjectOwnerKnowable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @OneToMany(mappedBy = "userHaveToSubmit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submit> submits = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "DONE")
    private boolean done = false;

    @Override
    public User ownerOfObject() {
        return this.user;
    }

    public List<Submit> getSubmitOf(User user) {
        return this.submits.stream().filter(submit -> submit.ownerOfObject().equals(user)).collect(Collectors.toList());
    }
}
