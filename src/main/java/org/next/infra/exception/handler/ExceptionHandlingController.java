package org.next.infra.exception.handler;

import org.next.infra.exception.*;
import org.next.infra.exception.unique.UniqueKeys;
import org.next.infra.reponse.ResponseCode;
import org.next.infra.result.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler(LoginNeededException.class)
    public Result loginException(LoginNeededException e) {
        return new Result(ResponseCode.LOGIN_NEEDED);
    }

    @ExceptionHandler(WrongAccessException.class)
    public Result wrongAccessException(WrongAccessException e) {
        return new Result(ResponseCode.WRONG_ACCESS);
    }

    @ExceptionHandler(NotExistException.class)
    public Result wrongAccessException(NotExistException e) {
        return new Result(ResponseCode.RESOURCE_NOT_EXIST);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public Result InvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        return new Result(ResponseCode.WRONG_ACCESS);
    }

    @ExceptionHandler(HasNoRightException.class)
    public Result hasNoRightException(HasNoRightException e) {
        return new Result(ResponseCode.UNAUTHORIZED_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public Result ConstraintViolationException(ConstraintViolationException e) {
        return new Result(ResponseCode.PATTERN_NOT_MATCHED, e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ExceptionHandler(PatternNotMatchedException.class)
    public Result patternNotMatchedException(PatternNotMatchedException e) {
        return new Result(ResponseCode.PATTERN_NOT_MATCHED, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointer(NullPointerException e) {
        return new Result(ResponseCode.WRONG_ACCESS, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new Result(ResponseCode.WRONG_ACCESS, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result dataIntegrityViolationException(DataIntegrityViolationException e) {
        return new Result(ResponseCode.DATA_INTEGRITY_ERROR, UniqueKeys.getErrorMessage(e.getCause().getCause().getMessage()));
    }

    @ExceptionHandler(RelativeUpdateBlockedException.class)
    public Result dataIntegrityViolationException(RelativeUpdateBlockedException e) {
        return new Result(ResponseCode.ContentRelation.UPDATE_BLOCKED);
    }

}
