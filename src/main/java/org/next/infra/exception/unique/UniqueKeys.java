package org.next.infra.exception.unique;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueKeys {

    private static final Pattern pattern;

    static {
        pattern = Pattern.compile("'(\\w+)'$");
    }

    public static final Map<String, String> messageMap;

    public static final String LECTURE_NAME_ALREADY_EXIST = "LECTURE_NAME_ALREADY_EXIST";
    public static final String USER_EMAIL_ALREADY_EXIST = "USER_EMAIL_ALREADY_EXIST";
    public static final String USER_GROUP_NAME_ALREADY_EXIST = "USER_GROUP_NAME_ALREADY_EXIST";
    public static final String CONTENT_GROUP_NAME_ALREADY_EXIST = "CONTENT_GROUP_NAME_ALREADY_EXIST";

    static {
        messageMap = new HashMap<>();
        messageMap.put(LECTURE_NAME_ALREADY_EXIST, "이미 존재하는 강의명입니다.");
        messageMap.put(USER_EMAIL_ALREADY_EXIST, "이미 존재하는 이메일입니다.");
        messageMap.put(USER_GROUP_NAME_ALREADY_EXIST, "유저그룹명이 중복되었습니다.");
        messageMap.put(CONTENT_GROUP_NAME_ALREADY_EXIST, "컨텐트그룹명이 중복되었습니다.");
    }

    public static String getErrorMessage(String message) {
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return messageMap.get(matcher.group(1));
        }
        return null;
    }
}
