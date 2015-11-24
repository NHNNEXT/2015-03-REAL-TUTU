package org.next.lms.user.inject;

import org.next.infra.exception.LoginNeededException;
import org.next.lms.user.User;
import org.next.lms.user.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoggedUserInjector implements HandlerMethodArgumentResolver {

    public static void setUserIdToSession(HttpSession session, Long id) {
        session.setAttribute(LOGIN_ACCOUNT_ID, id);
    }

    public static final String LOGIN_ACCOUNT_ID = "loginAccountId";

    private UserRepository userRepository;

    public LoggedUserInjector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Logged.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        boolean makeException = methodParameter.getParameterAnnotation(Logged.class).makeLoginNeededException();
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return nullHandling(makeException);
        }
        Long id = (Long) session.getAttribute(LOGIN_ACCOUNT_ID);
        if (id == null) {
            return nullHandling(makeException);
        }
        User user = userRepository.findOne(id);
        if (user == null) {
            return nullHandling(makeException);
        }
        return user;
    }

    private Object nullHandling(boolean makeException) {
        if (makeException)
            throw new LoginNeededException();
        return null;
    }
}
