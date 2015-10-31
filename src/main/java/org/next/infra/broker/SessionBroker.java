package org.next.infra.broker;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionBroker {

    private static final String LOGIN_ACCOUNT_ID = "loginAccountId";

    public Long getLoginAccountId(HttpSession session) {
        return getTypeSafeSessionValue(session, LOGIN_ACCOUNT_ID);
    }

    @SuppressWarnings("unchecked")
    private <T> T getTypeSafeSessionValue(HttpSession session, String key) {
        return (T) session.getAttribute(key);
    }

    public void setLoginAccountId(HttpSession session, Long loginAccountId) {
        session.setAttribute(LOGIN_ACCOUNT_ID, loginAccountId);
    }
}
