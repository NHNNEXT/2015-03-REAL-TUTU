package org.next.infra.util;

public class HanGuelUtil {

    public enum JosaType {
        은는, 이가, 을를, 으로_로, 와과
    }

    public static String josa(String keyWord, JosaType josaType) {
        char lastWord = extractLastWord(keyWord);

        if(!isHangle(lastWord))
            return "";

        return properJosa(josaType, containsJongSung(lastWord));
    }

    private static String properJosa(JosaType josaType, boolean hasJongSung) {
        switch (josaType) {
            case 은는:
                return hasJongSung ? "은" : "는";
            case 이가:
                return hasJongSung ? "이" : "가";
            case 을를:
                return hasJongSung ? "을" : "를";
            case 으로_로:
                return hasJongSung ? "으로" : "로";
            case 와과:
                return hasJongSung ? "과" : "와";
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