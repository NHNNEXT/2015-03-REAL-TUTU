package org.next.lms.user.domain;

public enum AccountState {
    ACTIVE,

    // 이메일 인증 대기
    WAIT_FOR_EMAIL_APPROVAL,

    // 회원 탈퇴
    WITHDRAWAL
}
