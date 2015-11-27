package org.next.lms.message.structure;

public abstract class ImmutableMessageTemplate extends AbstractMessageTemplate {

    @Override
    public Boolean isUpdatableMessage() {
        return false;
    }

}
