package org.next.infra.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.next.infra.user.controller.ErrorResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {

    public static boolean notNull(Object obj) {
        return obj != null;
    }

    public static List<Long> stringIdArrayToIdList(String arrayString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(arrayString, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
        } catch (IOException e) {
            throw new ErrorResponse("알수없는 오류가 발생하였습니다.");
        }
    }

}
