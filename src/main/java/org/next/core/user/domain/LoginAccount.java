package org.next.core.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.next.core.user.dto.LoginToken;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOGIN_ACCOUNT")
public class LoginAccount {

    // Relation
    @OneToOne
    @JoinColumn(name = "USER_INFO_ID")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "loginAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

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

    public void setLoginToken(LoginToken loginToken) {
        setEmailId(loginToken.getEmail());
        setPassword(loginToken.getPassword());
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

    public void addAuthority(Authority authority) {
        this.userAuthorities.add(new UserAuthority(authority, this));
    }

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
                ", id=" + id +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", state=" + state +
                '}';
    }
}
