package org.next.lms.content.domain;

import org.next.infra.exception.PatternNotMatchedException;

public enum ContentType {
    NOTICE, GENERAL, SUBMIT, SCHEDULE;

    public boolean getSubmit() {
        return false;
    }

    public void validate(Content content) {
//        if (type.getEndTime() && this.endTime == null)
//            throw new PatternNotMatchedException("시간을 입력해주세요.");
//        if (type.getStartTime() && this.startTime == null)
//            throw new PatternNotMatchedException("시간을 입력해주세요.");
    }
}
