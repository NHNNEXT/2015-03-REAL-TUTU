package org.next.lms.term;

import lombok.Getter;

import java.util.Date;

@Getter
public class TermDto {
    private final Long id;
    private final Date start;
    private final Date end;
    private final String name;

    public TermDto(Term term) {
        this.id = term.getId();
        this.start = term.getStart();
        this.end = term.getEnd();
        this.name = term.getName();
    }
}
