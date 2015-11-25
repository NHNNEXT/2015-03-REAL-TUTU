package org.next.infra.result;

import lombok.Getter;
import org.next.infra.reponse.ResponseCode;

@Getter
public class UploadResult {

    private Integer code;
    private String link;

    public UploadResult(Integer code) {
        this.code = code;
    }

    public UploadResult(String uglifiedFileName) {
        this.code = ResponseCode.SUCCESS;
        this.link = uglifiedFileName;
    }
}
