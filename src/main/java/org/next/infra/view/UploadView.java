package org.next.infra.view;

import lombok.Getter;
import org.next.infra.reponse.ResponseCode;

@Getter
public class UploadView {

    private Integer code;
    private String link;

    public UploadView(Integer code) {
        this.code = code;
    }

    public UploadView(String uglifiedFileName) {
        this.code = ResponseCode.SUCCESS;
        this.link = uglifiedFileName;
    }
}
