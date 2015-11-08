package org.next.infra.broker;

import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.LoginAccountRepository;
import org.next.lms.exception.LoginNeededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserInfoBroker {

    static final String LOGIN_ACCOUNT_ID = "loginAccountId";

    @Autowired
    private LoginAccountRepository loginAccountRepository;

    public LoginAccount getLoginAccount(HttpSession session) {
        Long id = (Long) session.getAttribute(LOGIN_ACCOUNT_ID);
        if (id == null)
            return null;
        return loginAccountRepository.findOne(id);
    }

    public UserInfo getUserInfo(HttpSession session) {
        LoginAccount account = getLoginAccount(session);
        if (account == null)
            throw new LoginNeededException();
        return account.getUserInfo();
    }

    public void setUserIdToSession(HttpSession session, Long id) {
        session.setAttribute(LOGIN_ACCOUNT_ID, id);
    }
}
