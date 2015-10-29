package org.next.infra.broker;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionBroker {

    public static final String LOGIN_ACCOUNT_ID = "loginAccountId";

    public Long getLoginAccountId(HttpSession session) {
        return getTypeSafeSessionValue(session, LOGIN_ACCOUNT_ID);
    }

    @SuppressWarnings("unchecked")
    private <T> T getTypeSafeSessionValue(HttpSession session, String key) {
        return (T) session.getAttribute(key);
    }
}
