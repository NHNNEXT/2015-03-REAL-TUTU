package org.next.core.user.domain;

public enum AuthorityType {

    // 제제당한 상태
    ROLE_BANNED,

    // 미인증 회원
    ROLE_NOT_AUTHORIZED,

    // 학생
    ROLE_STUDENT,

    // 수업 조교
    ROLE_TEACHING_ASSISTANT,

    // 교수
    ROLE_PROFESSOR,

    // 시스템 관리자
    ROLE_SYSTEM_MANAGER
}
