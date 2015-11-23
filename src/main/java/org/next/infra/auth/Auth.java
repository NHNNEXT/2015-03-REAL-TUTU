package org.next.infra.auth;

import org.next.infra.exception.HasNoRightException;

public class Auth {

    protected final void rightCheck(boolean right) {
        if (right)
            return;
        throw new HasNoRightException();
    }
}
