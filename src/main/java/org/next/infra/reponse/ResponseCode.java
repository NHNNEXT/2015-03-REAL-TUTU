package org.next.infra.reponse;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseCode {

    public static final Integer SUCCESS = 0;


    public static final class Register {
        public static final Integer ALREADY_EXIST_EMAIL = 1;
    }

    public static final class Login {
        public static final Integer NOT_EXIST_EMAIL = 1;
        public static final Integer WRONG_PASSWORD = 2;
        public static final Integer WITHDRAWAL_ACCOUNT = 3;
    }

    public static final class GetSessionUser {
        public static final Integer EMPTY = 1;
    }

    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        return parseClass(ResponseCode.class);
    }

    private static Map<String, Object> parseClass(Class clazz) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field field : clazz.getFields())
            map.put(field.getName(), field.get(null));
        for (Class cLass : clazz.getDeclaredClasses())
            map.put(cLass.getSimpleName(), parseClass(cLass));
        return map;
    }
}
