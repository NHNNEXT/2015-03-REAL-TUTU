package org.next.lms.message.structure;

public abstract class MutableMessageTemplate extends AbstractMessageTemplate {

    @Override
    public Boolean isUpdatableMessage() {
        return true;
    }

}
