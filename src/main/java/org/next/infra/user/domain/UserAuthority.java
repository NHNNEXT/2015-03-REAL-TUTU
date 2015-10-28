package org.next.infra.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"authority", "loginAccount"})
@Entity
@Table(name = "USER_AUTHORITY")
public class UserAuthority {

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private Authority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGIN_ACCOUNT_ID")
    private LoginAccount loginAccount;

    // Field
    @Id
    @Column(name = "USER_AUTHORITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public UserAuthority(Authority authority, LoginAccount loginAccount) {
        this.authority = authority;
        this.loginAccount = loginAccount;
    }
}
