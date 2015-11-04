package org.next.infra.broker;

import org.next.infra.user.domain.LoginAccount;
import org.next.infra.user.domain.UserInfo;
import org.next.infra.user.repository.LoginAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserInfoBroker {

    @Autowired
    private LoginAccountRepository loginAccountRepository;

    @Autowired
    private SessionBroker sessionBroker;

    public Long getLoginAccountId(HttpSession session) {
        return sessionBroker.getLoginAccountId(session);
    }

    public LoginAccount getLoginAccount(HttpSession session) {
        Long id = (Long) session.getAttribute(SessionBroker.LOGIN_ACCOUNT_ID);
        if (id == null)
            return null;
        return loginAccountRepository.findOne(getLoginAccountId(session));
    }

    public UserInfo getUserInfo(HttpSession session) {
        return getLoginAccount(session).getUserInfo();
    }
}
