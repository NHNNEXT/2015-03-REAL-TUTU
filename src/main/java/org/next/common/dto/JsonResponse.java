package org.next.common.dto;

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
