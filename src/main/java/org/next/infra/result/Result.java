package org.next.infra.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.next.infra.reponse.ResponseCode;

@Getter
@NoArgsConstructor
public class Result {

    private String err;
    private Object result;
    private Integer code;

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, Object object) {
        this.code = code;
        this.result = object;
    }


    public static Result success() {
        return success(ResponseCode.SUCCESS);
    }

    public static Result success(Object result) {
        Result response = new Result();
        response.setResult(result);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }


    public Result setErr(String errorMessage) {
        this.err = errorMessage;
        return this;
    }

    public Result setResult(Object result) {
        this.result = result;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
