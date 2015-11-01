package org.next.infra.common.domain;

import lombok.Getter;

@Getter
public enum MajorType {

    MAJOR_CORE(0), MAJOR_SELECTIVE(1), GENERAL_CORE(2), GENERAL_SELECTIVE(4);

    private Integer value;

    MajorType(Integer value) {
        this.value = value;
    }
}
