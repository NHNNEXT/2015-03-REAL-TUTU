package org.next.lms.submit;

import lombok.*;
import org.next.infra.auth.ObjectOwnerKnowable;
import org.next.lms.content.domain.Content;
import org.next.lms.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"user", "content", "submits"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "content", "submits"})
@Entity
@Table(name = "USER_HAVE_TO_SUBMIT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "CONTENT_ID"})
})
public class UserHaveToSubmit implements ObjectOwnerKnowable{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @OneToMany(mappedBy = "userHaveToSubmit", fetch = FetchType.LAZY)
    private List<Submit> submits = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public User ownerOfObject() {
        return this.user;
    }
}
