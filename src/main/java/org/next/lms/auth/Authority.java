package org.next.lms.auth;

import org.next.lms.exception.HasNoRightException;

public class Authority {

    void rightCheck(boolean right) {
        if (right)
            return;
        throw new HasNoRightException();
    }

}
