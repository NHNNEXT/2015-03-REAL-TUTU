package org.next.common.response;

import lombok.*;

@Getter
public class JsonResponse {
    private String err;
    private Object result;

    public JsonResponse(String err, Object result){
        this.err = err;
        this.result = result;
    }
}
