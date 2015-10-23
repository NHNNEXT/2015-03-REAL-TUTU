package org.next.core.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"userAuthorities"})
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userAuthorities"})
@Entity
@Table(name = "AUTHORITY")
public class Authority {

    // Relation
    @OneToMany(mappedBy = "authority", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    // Field
    @Id
    @Column(name = "AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Authority Type Must Not Be Null")
    @Column(name = "AUTHORITY_TYPE")
    private AuthorityType authorityType;

    public Authority(Long id, AuthorityType authorityType) {
        this.id = id;
        this.authorityType = authorityType;
    }
}
