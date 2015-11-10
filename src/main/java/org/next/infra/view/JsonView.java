package org.next.infra.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.next.infra.reponse.ResponseCode;

@Getter
@NoArgsConstructor
public class JsonView {

    public static final String DEFAULT_ERROR_MESSAGE = "Error.";
    public static final String DEFAULT_SUCCESS_MESSAGE = "Success.";

    private String err;
    private Object result;
    private Integer code;

    public JsonView(Integer code) {
        this.code = code;
    }

    public JsonView(Integer code, Object object) {
        this.code = code;
        this.result = object;
    }

    public static JsonView errorJsonResponse() {
        return errorJsonResponse(DEFAULT_ERROR_MESSAGE);
    }

    public static JsonView errorJsonResponse(String errorMessage) {
        return new JsonView().setErr(ensureMessageEndWithPeriod(errorMessage));
    }

    public static JsonView successJsonResponse() {
        return successJsonResponse(ResponseCode.SUCCESS);
    }

    public static JsonView successJsonResponse(Object result) {
        JsonView response = new JsonView();
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

    public JsonView setErr(String errorMessage) {
        this.err = errorMessage;
        return this;
    }

    public JsonView setResult(Object result) {
        this.result = result;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
