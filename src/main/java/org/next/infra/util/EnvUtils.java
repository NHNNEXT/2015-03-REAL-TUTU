package org.next.infra.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvUtils {

    @Autowired
    private Environment environment;

    public String getAbsoluteURIPath(String apiPath) {
        return environment.getProperty("SERVICE_DOMAIN") + apiPath;
    }
}
