package org.next.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.exception.WrongAccessException;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.toIntExact;

public class CommonUtils {

    public static <T> T assureNotNull(T obj) {
        if (obj == null)
            throw new WrongAccessException();
        return obj;
    }

    public static void assureTrue(boolean bool) {
        if (!bool)
            throw new WrongAccessException();
    }

    public static <T> List<T> parseList(Class<T> clazz, String arrayString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(arrayString, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get Random Generated Number
     *
     * @param maxValue Upper boundary of the random values
     * @return value of 0 to maxValue
     */
    public static int random(int maxValue) {
        return toIntExact(Math.round((Math.random() * maxValue)));
    }
}
