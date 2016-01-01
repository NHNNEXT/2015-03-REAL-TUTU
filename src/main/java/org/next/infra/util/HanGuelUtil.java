package org.next.infra.util;

/**
 * 유니코드 한글은 0xAC00 으로부터
 * 초성 19개, 중상21개, 종성28개로 이루어지고
 * 이들을 조합한 11,172개의 문자를 갖는다.
 * <p>
 * 한글코드의 값 = ((초성 * 21) + 중성) * 28 + 종성 + 0xAC00
 * (0xAC00은 'ㄱ'의 코드값)
 * <p>
 * 따라서 다음과 같은 계산 식이 구해진다.
 * 유니코드 한글 문자 코드 값이 X일 때,
 * <p>
 * 초성 = ((X - 0xAC00) / 28) / 21
 * 중성 = ((X - 0xAC00) / 28) % 21
 * 종성 = (X - 0xAC00) % 28
 * <p>
 * 이 때 초성, 중성, 종성의 값은 각 소리 글자의 코드값이 아니라
 * 이들이 각각 몇 번째 문자인가를 나타내기 때문에 다음과 같이 다시 처리한다.
 * <p>
 * 초성문자코드 = 초성 + 0x1100 //('ㄱ')
 * 중성문자코드 = 중성 + 0x1161 // ('ㅏ')
 * 종성문자코드 = 종성 + 0x11A8 - 1 // (종성이 없는 경우가 있으므로 1을 뺌)
 **/
public class HanGuelUtil {

    public enum JosaType {
        은는, 이가, 을를, 으로_로, 와과
    }

    public static String josa(String keyWord, JosaType josaType) {
        char lastWord = extractLastWord(keyWord);

        if (!isHangle(lastWord))
            return "";

        return properJosa(josaType, containsJongSung(lastWord));
    }

    public static String josaWhenEllipsisApplied(String keyWord, JosaType josaType, int length) {
        if (keyWord.length() > length) {
            String reducedWord = keyWord.substring(0, length - 1);
            return josa(reducedWord, josaType);
        }
        return josa(keyWord, josaType);
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

    public static boolean isHangle(char lastWord) {
        return lastWord > 11172;
    }

    public static char extractChoSung(char unicodeWord) {
        return (char) ((((unicodeWord - 0xAC00) / 28) / 21) + 0x1100);
    }

    public static char extractJungSung(char unicodeWord) {
        return (char) ((((unicodeWord - 0xAC00) / 28) % 21) + 0x1161);
    }

    public static char extractJongSung(char unicodeWord) {
        return (char) ((unicodeWord - 0xAC00) % 28 + 0x11A8 - 1);
    }

    public static boolean containsJongSung(char unicodeWord) {
        char jongSung = extractJongSung(unicodeWord);
        return notBlank(jongSung);
    }

    private static boolean notBlank(char unicodeWord) {
        return unicodeWord != 4519;
    }
}