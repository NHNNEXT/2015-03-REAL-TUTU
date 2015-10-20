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
    @Column(name = "EMAIL_ID")
    private String emailId;

    @NotNull(message = "User Password Must Not Be Null")
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User Account Type Must Not Be Null")
    @Column(name = "USER_ACCOUNT_TYPE")
    private AccountType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User State Type Must Not Be Null")
    @Column(name = "USER_ACCOUNT_STATE")
    private AccountStateType state;

    public boolean login(LoginAccount loginAccount) {
        return this.emailId.equals(loginAccount.emailId) && this.password.equals(loginAccount.password);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Long getId() {
        return id;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getType() {
        return type;
    }

    public AccountStateType getState() {
        return state;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setState(AccountStateType state) {
        this.state = state;
    }
}
