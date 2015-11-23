package org.next.lms.lecture.auth;

// TODO 클라이언트에서는 숫자로된 코드가 필요하지 않은것으로 보이는데 Enum을 사용하는것이 좋지 않을까?
public class ApprovalState {
    public static final Integer OK = 0;
    public static final Integer WAITING_APPROVAL = 1;
    public static final Integer REJECT = 2;
}
