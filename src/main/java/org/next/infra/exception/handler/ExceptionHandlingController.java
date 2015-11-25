package org.next.infra.exception.handler;

import org.next.infra.exception.HasNoRightException;
import org.next.infra.exception.LoginNeededException;
import org.next.infra.exception.PatternNotMatchedException;
import org.next.infra.exception.WrongAccessException;
import org.next.infra.result.Result;
import org.next.infra.reponse.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler(LoginNeededException.class)
    public Result loginException(Exception e) {
        return new Result(ResponseCode.LOGIN_NEEDED);
    }

    @ExceptionHandler(WrongAccessException.class)
    public Result wrongAccessException(Exception e) {
        return new Result(ResponseCode.WRONG_ACCESS);
    }

    @ExceptionHandler(HasNoRightException.class)
    public Result hasNoRightException(Exception e) {
        return new Result(ResponseCode.UNAUTHORIZED_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public Result patternNotMatched(ConstraintViolationException e) {
        return new Result(ResponseCode.PATTERN_NOT_MATCHED, e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ExceptionHandler(PatternNotMatchedException.class)
    public Result requireValueNull(PatternNotMatchedException e) {
        return new Result(ResponseCode.PATTERN_NOT_MATCHED, e.getMessage());
    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public JsonView nullPointer(NullPointerException e) {
//        return new JsonView(ResponseCode.WRONG_ACCESS, e.getMessage());
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public JsonView methodArgumentTypeMismatchException(NullPointerException e) {
//        return new JsonView(ResponseCode.WRONG_ACCESS, e.getMessage());
//    }
}
