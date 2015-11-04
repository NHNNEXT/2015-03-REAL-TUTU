package org.next.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CommonUtils {

    public static boolean notNull(Object obj) {
        return obj != null;
    }

    public static <T> List<T> parseList(Class<T> clazz, String arrayString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(arrayString, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            return null;
        }
    }

}
