package org.next.infra.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class CommonJsonResponse {
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
}
