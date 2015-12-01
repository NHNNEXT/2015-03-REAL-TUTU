package org.next.lms.content.domain;

import org.next.infra.exception.PatternNotMatchedException;

public enum ContentType {
    NOTICE(false, false), GENERAL(false, false), SUBMIT(false, true), SCHEDULE(true, true);

    private boolean endTime;
    private boolean startTime;

    private ContentType(boolean startTime, boolean endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void validate(Content content) {
        if (this.endTime && content.getEndTime() == null)
            throw new PatternNotMatchedException("시간을 입력해주세요.");
        if (this.startTime && content.getStartTime() == null)
            throw new PatternNotMatchedException("시간을 입력해주세요.");
    }
}
