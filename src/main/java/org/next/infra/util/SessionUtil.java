package org.next.infra.util;

import org.next.lms.user.User;
import org.next.lms.user.repository.UserRepository;
import org.next.infra.exception.LoginNeededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtil {

    static final String LOGIN_ACCOUNT_ID = "loginAccountId";

    @Autowired
    private UserRepository userRepository;

    public void setUserIdToSession(HttpSession session, Long id) {
        session.setAttribute(LOGIN_ACCOUNT_ID, id);
    }

    public User getLoggedUser(HttpSession session) {
        Long id = getId(session);
        if (id == null)
            throw new LoginNeededException();
        return userRepository.findOne(id);
    }

    public User getUser(HttpSession session) {
        Long id = getId(session);
        if (id == null)
            return null;
        return userRepository.findOne(id);
    }

    public Long getId(HttpSession session) {
        return (Long) session.getAttribute(LOGIN_ACCOUNT_ID);
    }
}
