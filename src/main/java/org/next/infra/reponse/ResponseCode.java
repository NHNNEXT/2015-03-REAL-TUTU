package org.next.infra.reponse;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseCode {

    public static final Integer SUCCESS = 0;

    public static final Integer WROING_ACCESS = 1;

    public static final Integer LOGIN_NEEDED = 13;


    public static final class Register {
        public static final Integer ALREADY_EXIST_EMAIL = 2;
    }

    public static final class Login {
        public static final Integer NOT_EXIST_EMAIL = 3;
        public static final Integer WRONG_PASSWORD = 4;
        public static final Integer WITHDRAWAL_ACCOUNT = 5;
    }

    public static final class GetSessionUser {
        public static final Integer EMPTY = 6;
    }

    public static final class FileUpload {
        public static final Integer FILE_NOT_ATTACHED = 7;
        public static final Integer ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT = 8;
    }


    public static final class Like {
        public static final Integer CONTENT = 9;
        public static final Integer LECTURE = 10;
        public static final Integer REPLY = 11;
        public static final Integer LESSON = 12;
        public static final Integer ADD = 14;
        public static final Integer REMOVE = 15;
    }


    /*


                ⊂_ヽ
            　 ＼＼ Λ＿Λ
            　　 ＼( 'ㅅ' ) 두둠칫
            　　　 >　⌒ヽ
            　　　/ 　 へ＼
            　　 /　　/　＼＼
            　　 ﾚ　ノ　　 ヽ_つ
            　　/　/ 두둠칫
            　 /　/|
            　(　(ヽ
            　|　|、＼
            　| 丿 ＼ ⌒)
            　| |　　) /
            (`ノ )　　Lﾉ


                                Directed By Sungho. Park

     */


    private static Map<String, ?> responseCode;

    static {
        try {
            responseCode = toJsonMap();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, ?> getResponseCodeMap() {
        return responseCode;
    }

    private static Map<String, ?> toJsonMap() throws IllegalAccessException {
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
