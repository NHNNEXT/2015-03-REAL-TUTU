package org.next.infra.common.domain;

import lombok.Getter;

@Getter
public enum Day {
    MON(0), TUE(1), WED(2), THUR(3), FRI(4), SAT(5), SUN(6);
    private Integer value;

    Day(Integer dayOfWeek) {
        this.value = dayOfWeek;
    }
}
