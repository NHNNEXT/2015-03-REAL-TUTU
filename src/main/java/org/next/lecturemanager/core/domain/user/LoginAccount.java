package org.next.lecturemanager.core.domain.user;

import lombok.*;
import org.next.lecturemanager.core.dto.user.LoginToken;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"userInfo", "userAuthorities"})
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(exclude = {"userInfo", "userAuthorities"})
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

    public void addAuthority(Authority authority) {
        this.userAuthorities.add(new UserAuthority(authority, this));
    }
}
