package org.next.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.exception.NotExistException;
import org.next.infra.exception.WrongAccessException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.toIntExact;

public class CommonUtils {

    public static <T> T assureNotNull(T obj) {
        if (obj == null)
            throw new WrongAccessException();
        return obj;
    }

    public static <T> T ifNullNotFoundErroReturn(T obj) {
        if (obj == null)
            throw new NotExistException();
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

    public static String getLikeExpression(String word) {
        return '%' + word + '%';
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

    /**
     * 긴 문자열에 대해 말 줄임 처리를 수행한다
     *
     * @param text   말 줄임 처리를 할 문자열
     * @param length 생략되면 안되는 길이
     * @return 말줄임 처리된 문자열
     */
    public static String ellipsis(String text, int length) {
        if (text.length() <= 0 || length <= 0)
            throw new InvalidParameterException("length 는 0 이하일 수 없습니다");

        if (text.length() > length) {
            String decoString = "...";
            String reduced = text.substring(0, length - 1);
            reduced += decoString;
            return reduced;
        }

        return text;
    }

    public static String makeUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
