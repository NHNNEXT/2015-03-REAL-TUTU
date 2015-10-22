package org.next.core.user;

import org.next.core.user.domain.AccountStateType;

public class LoginProcessStatus {
    private boolean logined;
    private AccountStateType accountStatus;

    public AccountStateType getAccountStatus() {
        return accountStatus;
    }

    public boolean isLogined() {
        return logined;
    }

    public LoginProcessStatus(boolean logined, AccountStateType accountStatus) {
        this.logined = logined;
        this.accountStatus = accountStatus;
    }
}
