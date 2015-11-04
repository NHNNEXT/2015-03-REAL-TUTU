package org.next.infra.user.domain;

public enum AuthorityType {

    // 제제당한 상태
    ROLE_BANNED,

    // 미인증 회원
    ROLE_NOT_AUTHORIZED,

    // 인증 회원
    ROLE_AUTHORIZED,

    // 시스템 관리자
    ROLE_SYSTEM_MANAGER
}