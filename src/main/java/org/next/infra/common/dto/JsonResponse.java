package org.next.infra.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class JsonResponse {
    private String err;
    private Object result;

    public JsonResponse setErr(String errorMessage) {
        this.err = errorMessage;
        return this;
    }

    public JsonResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
