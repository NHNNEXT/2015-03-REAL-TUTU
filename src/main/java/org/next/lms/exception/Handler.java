package org.next.lms.exception;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.reponse.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(LoginNeededException.class)
    public
    @ResponseBody
    CommonJsonResponse loginException(Exception e) {
        return new CommonJsonResponse(ResponseCode.LOGIN_NEEDED);
    }

    @ExceptionHandler(WrongAcessExeption.class)
    public
    @ResponseBody
    CommonJsonResponse wrongAccessException(Exception e) {
        return new CommonJsonResponse(ResponseCode.WROING_ACCESS);
    }

    @ExceptionHandler(HasNoRightException.class)
    public
    @ResponseBody
    CommonJsonResponse hasNoRightException(Exception e) {
        return new CommonJsonResponse(ResponseCode.UNAUTHORIZED_REQUEST);
    }
}
