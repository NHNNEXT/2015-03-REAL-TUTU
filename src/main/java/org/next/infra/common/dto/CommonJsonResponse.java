package org.next.infra.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class CommonJsonResponse {

    public static final String DEFAULT_ERROR_MESSAGE = "Error";
    public static final String DEFAULT_SUCCESS_MESSAGE = "Success";

    private String err;
    private Object result;

    public CommonJsonResponse setErr(String errorMessage) {
        this.err = errorMessage;
        return this;
    }

    public CommonJsonResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public static CommonJsonResponse errorJsonResponse() {
        return errorJsonResponse(DEFAULT_ERROR_MESSAGE);
    }

    public static CommonJsonResponse errorJsonResponse(String errorMessage) {
        return new CommonJsonResponse().setErr(errorMessage);
    }

    public static CommonJsonResponse successJsonResponse() {
        return successJsonResponse(DEFAULT_SUCCESS_MESSAGE);
    }

    public static CommonJsonResponse successJsonResponse(Object result) {
        return new CommonJsonResponse().setResult(result);
    }
}
