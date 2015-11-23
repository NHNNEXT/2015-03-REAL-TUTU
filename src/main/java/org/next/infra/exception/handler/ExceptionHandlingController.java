package org.next.infra.exception.handler;

import org.next.infra.exception.HasNoRightException;
import org.next.infra.exception.LoginNeededException;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.exception.WrongAccessException;
import org.next.infra.view.JsonView;
import org.next.infra.reponse.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler(LoginNeededException.class)
    public JsonView loginException(Exception e) {
        return new JsonView(ResponseCode.LOGIN_NEEDED);
    }

    @ExceptionHandler(WrongAccessException.class)
    public JsonView wrongAccessException(Exception e) {
        return new JsonView(ResponseCode.WRONG_ACCESS);
    }

    @ExceptionHandler(HasNoRightException.class)
    public JsonView hasNoRightException(Exception e) {
        return new JsonView(ResponseCode.UNAUTHORIZED_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public JsonView patternNotMatched(ConstraintViolationException e) {
        return new JsonView(ResponseCode.PATTERN_NOT_MATCHED, e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ExceptionHandler(PatternNotMatchedException.class)
    public JsonView requireValueNull(PatternNotMatchedException e) {
        return new JsonView(ResponseCode.PATTERN_NOT_MATCHED, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public JsonView nullPointer(NullPointerException e) {
        return new JsonView(ResponseCode.WRONG_ACCESS, e.getMessage());
    }
}
