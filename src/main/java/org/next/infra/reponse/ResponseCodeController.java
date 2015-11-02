package org.next.infra.reponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/responseCode")
public class ResponseCodeController {


    @PermitAll
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, ?> getResponseCode() throws IllegalAccessException {
        return ResponseCode.toJsonMap();
    }

}
