package org.next.core.user.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "LOGIN_ACCOUNT")
public class LoginAccount {

    // Relation
    @OneToOne
    @JoinColumn(name = "USER_INFO_ID")
    private UserInfo userInfo;

    // Field
    @Id
    @Column(name = "LOGIN_ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "User Email Id Must Not Be Null")
    @Column(name = "USER_EMAIL_ID")
    private String emailId;

    @NotNull(message = "User Password Must Not Be Null")
    @Column(name = "USER_PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User Account Type Must Not Be Null")
    @Column(name = "USER_ACCOUNT_TYPE")
    private AccountType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User State Type Must Not Be Null")
    @Column(name = "USER_ACCOUNT_STATE")
    private UserStateType state;

    public boolean login(LoginAccount loginAccount) {
        return this.emailId.equals(loginAccount.emailId) && this.password.equals(loginAccount.password);
    }
}
