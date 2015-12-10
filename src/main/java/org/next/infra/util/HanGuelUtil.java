package org.next.infra.util;

public class HanGuelUtil {

    public enum JosaType {
        은는, 이가, 을를, 으로_로, 와과
    }

    public enum ObjectType {
        HUMAN, OBJECT
    }

    public static String josa(String keyWord, JosaType josaType, ObjectType objectType) {
        char lastWord = extractLastWord(keyWord);

        if(!isHangle(lastWord))
            return keyWord;

        if(containsJongSung(lastWord)) {
            return hasJongSungData(josaType, objectType);
        } else {
            return hasNoJongSungData(josaType);
        }
    }

    private static String hasJongSungData(JosaType josaType, ObjectType human) {
        switch (josaType) {
            case 은는:
                return (human == ObjectType.HUMAN) ? "이는" : "은";
            case 이가:
                return (human == ObjectType.HUMAN) ? "이가" : "이";
            case 을를:
                return (human == ObjectType.HUMAN) ? "이를" : "을";
            case 으로_로:
                return (human == ObjectType.HUMAN) ? "으로" : "으로";
            case 와과:
                return "과";
            default:
                return "";
        }
    }

    private static String hasNoJongSungData(JosaType josaType) {
        switch (josaType) {
            case 은는:
                return "는";
            case 이가:
                return "가";
            case 을를:
                return "를";
            case 으로_로:
                return "로";
            case 와과:
                return "와";
            default:
                return "";
        }
    }

    private static char extractLastWord(String keyWord) {
        return keyWord.charAt(keyWord.length() - 1);
    }

    private static boolean isHangle(char lastWord) {
        return lastWord <= 11172;
    }

    private static char extractChoSung(char unicodeWord) {
        return (char) ((((unicodeWord - (unicodeWord % 28)) / 28) / 21) + 0x1100);
    }

    private static char extractJungSung(char unicodeWord) {
        return (char) ((((unicodeWord - (unicodeWord % 28)) / 28) % 21) + 0x1161);
    }

    private static char extractJongSung(char unicodeWord) {
        return (char) ((unicodeWord % 28) + 0x11a7);
    }

    private static boolean containsJongSung(char unicodeWord) {
        char jongSung = extractJongSung(unicodeWord);
        return notBlank(jongSung);
    }

    private static boolean notBlank(char unicodeWord) {
        return unicodeWord != 4519;
    }
}