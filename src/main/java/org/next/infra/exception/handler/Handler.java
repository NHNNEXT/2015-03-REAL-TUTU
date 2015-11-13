package org.next.infra.exception.handler;

import org.next.infra.exception.HasNoRightException;
import org.next.infra.exception.LoginNeededException;
import org.next.infra.exception.WrongAccessException;
import org.next.infra.view.JsonView;
import org.next.infra.reponse.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(LoginNeededException.class)
    public
    @ResponseBody
    JsonView loginException(Exception e) {
        return new JsonView(ResponseCode.LOGIN_NEEDED);
    }

    @ExceptionHandler(WrongAccessException.class)
    public
    @ResponseBody
    JsonView wrongAccessException(Exception e) {
        return new JsonView(ResponseCode.WRONG_ACCESS);
    }

    @ExceptionHandler(HasNoRightException.class)
    public
    @ResponseBody
    JsonView hasNoRightException(Exception e) {
        return new JsonView(ResponseCode.UNAUTHORIZED_REQUEST);
    }
}
