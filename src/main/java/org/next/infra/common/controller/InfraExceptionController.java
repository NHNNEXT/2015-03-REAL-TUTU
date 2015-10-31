package org.next.infra.common.controller;

import org.next.infra.common.dto.CommonJsonResponse;
import org.next.infra.user.controller.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class InfraExceptionController {

    @ExceptionHandler(ErrorResponse.class)
    public @ResponseBody CommonJsonResponse ErrorResponseException(Exception e) {
        return CommonJsonResponse.errorJsonResponse(e.getMessage());
    }
}