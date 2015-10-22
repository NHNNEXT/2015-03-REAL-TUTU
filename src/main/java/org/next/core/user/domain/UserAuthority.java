package org.next.core.user.domain;

import javax.persistence.*;

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

    public UserAuthority() {}

    public UserAuthority(Authority authority, LoginAccount loginAccount) {
        this.authority = authority;
        this.loginAccount = loginAccount;
    }

    public Authority getAuthority() {
        return authority;
    }
}
