package org.next.lms.message.structure;

public abstract class MultipleEventReportMessageTemplate extends MutableMessageTemplate {

    private Integer eventOccurrenceCount;

    public MultipleEventReportMessageTemplate(Integer eventOccurrenceCount) {
        this.eventOccurrenceCount = eventOccurrenceCount;
    }

    @Override
    protected final String getMessageString() {
        if (eventOccurrenceCount < 2)
            return singleEventMessage();
        return multipleEventMessage();
    }

    public Integer getEventOccurrenceCount() {
        return eventOccurrenceCount;
    }

    protected abstract String singleEventMessage();
    protected abstract String multipleEventMessage();
}