package org.next.common.response;

public class JsonResponse {
    private String err;
    private Object result;

    public JsonResponse(String err, Object result){
        this.err = err;
        this.result = result;
    }

    public String getErr() {
        return err;
    }

    public Object getResult() {
        return result;
    }
}
