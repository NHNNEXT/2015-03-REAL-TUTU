package org.next.infra.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.next.infra.reponse.ResponseCode;

@Getter
@NoArgsConstructor
public class Result {

    private Integer code;
    private Object result;

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
        return new Result(ResponseCode.SUCCESS, result);
    }
}
