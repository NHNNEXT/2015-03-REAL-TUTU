package org.next.infra.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class CommonJsonResponse {

    public static final String DEFAULT_ERROR_MESSAGE = "Error.";
    public static final String DEFAULT_SUCCESS_MESSAGE = "Success.";

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
        return new CommonJsonResponse().setErr(ensureMessageEndWithPeriod(errorMessage));
    }

    public static CommonJsonResponse successJsonResponse() {
        return successJsonResponse(DEFAULT_SUCCESS_MESSAGE);
    }

    public static CommonJsonResponse successJsonResponse(Object result) {
        if(stringInstance(result)) {
            return new CommonJsonResponse().setResult(ensureMessageEndWithPeriod((String) result));
        }
        return new CommonJsonResponse().setResult(result);
    }

    private static String ensureMessageEndWithPeriod(String message) {
        boolean endWithPeriod = message.trim().endsWith(".");
        return endWithPeriod ? message : (message + ".");
    }

    private static boolean stringInstance(Object result) {
        return result instanceof String;
    }
}
