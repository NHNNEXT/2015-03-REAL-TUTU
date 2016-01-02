package org.next.infra.reponse;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseCode {
    public static final Integer SUCCESS = 0;
    public static final Integer WRONG_ACCESS = 1;
    public static final Integer LOGIN_NEEDED = 13;
    public static final Integer UNAUTHORIZED_REQUEST = 100;
    public static final Integer PATTERN_NOT_MATCHED = 101;
    public static final Integer DATA_INTEGRITY_ERROR = 199;
    public static final Integer RESOURCE_NOT_EXIST = 299;

    public static final class Register {
        public static final Integer ALREADY_EXIST_EMAIL = 2;
        public static final Integer ACCOUNT_IS_WAITING_FOR_EMAIL_CERTIFY = 45;
    }

    public static final class Login {
        public static final Integer DO_EMAIL_VERIFY_FIRST = 40;
        public static final Integer NOT_EXIST_EMAIL = 3;
        public static final Integer WRONG_PASSWORD = 4;
        public static final Integer WITHDRAWAL_ACCOUNT = 5;
    }

    public static final class MailRequest {
        public static final Integer USER_NOT_EXIST = 44;
        public static final Integer USER_ALREADY_VERIFIED = 47;
        public static final Integer ALREADY_PASSWORD_CHANGE_MAIL_SENT = 43;
        public static final Integer USER_NOT_VERIFIED = 45;
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
        public static final Integer ADD = 14;
        public static final Integer REMOVE = 15;
    }

    public static final class Enroll {
        public static final Integer WAITING_FOR_APPROVAL = 16;
    }

    public static final class ContentRelation {
        public static final Integer ALREADY_EXIST = 29;
        public static final Integer CANT_BIND_SELF = 30;
    }

    public static final class Node {
        public static final Integer CHILD_EXIST = 39;
    }


    public static final class UserAuth {
        public static final Integer MAIL_EXPIRED = 214;
        public static final Integer KEY_NOT_MATCHED = 229;
        public static final Integer USER_ALREADY_VERIFIED = 230;
    }

    public static final class PasswordChange {
        public static final Integer TOO_MANY_TRY = 215;
        public static final Integer KEY_NOT_MATCHED = 239;
        public static final Integer EXPIRED = 216;
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



     */


    private static Map<String, ?> responseCode;

    static {
        responseCode = toJsonMap();
    }

    public static Map<String, ?> getResponseCodeMap() {
        return responseCode;
    }

    private static Map<String, ?> toJsonMap() {
        try {
            return parseClass(ResponseCode.class);
        } catch (IllegalAccessException e) {
            // TODO Return Error명 논의필요
            log.error("ResponseCode Json Map 파싱 과정에서 오류 발생");
            throw new RuntimeException("[Return Error명 논의필요] ResponseCode Json Map 파싱 과정에서 오류 발생");
        }
    }

    private static Map<String, Object> parseClass(Class clazz) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field field : clazz.getFields())
            map.put(field.getName(), field.get(null));
        for (Class innerClass : clazz.getDeclaredClasses())
            map.put(innerClass.getSimpleName(), parseClass(innerClass));
        return map;
    }

}
