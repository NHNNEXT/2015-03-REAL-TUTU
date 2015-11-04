package org.next.infra.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.next.infra.reponse.ResponseCode;

@Getter
@NoArgsConstructor
public class CommonJsonResponse {

    public static final String DEFAULT_ERROR_MESSAGE = "Error.";
    public static final String DEFAULT_SUCCESS_MESSAGE = "Success.";

    private String err;
    private Object result;
    private Integer code;

    public CommonJsonResponse(Integer code) {
        this.code = code;
    }

    public CommonJsonResponse(Integer code, Object object) {
        this.code = code;
        this.result = object;
    }

    public static CommonJsonResponse errorJsonResponse() {
        return errorJsonResponse(DEFAULT_ERROR_MESSAGE);
    }

    public static CommonJsonResponse errorJsonResponse(String errorMessage) {
        return new CommonJsonResponse().setErr(ensureMessageEndWithPeriod(errorMessage));
    }

    public static CommonJsonResponse successJsonResponse() {
        return successJsonResponse(ResponseCode.SUCCESS);
    }

    public static CommonJsonResponse successJsonResponse(Object result) {
        CommonJsonResponse response = new CommonJsonResponse();
        response.setResult(result);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

    private static String ensureMessageEndWithPeriod(String message) {
        boolean endWithPeriod = message.trim().endsWith(".");
        return endWithPeriod ? message : (message + ".");
    }

    private static boolean stringInstance(Object result) {
        return result instanceof String;
    }

    public CommonJsonResponse setErr(String errorMessage) {
        this.err = errorMessage;
        return this;
    }

    public CommonJsonResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
