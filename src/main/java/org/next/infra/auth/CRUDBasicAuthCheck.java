package org.next.infra.auth;

import org.next.lms.user.domain.User;

import java.util.Arrays;

public class CRUDBasicAuthCheck extends AuthCheck {

    public void checkWriteRight(ObjectOwnerKnowable objectOwnerKnowable, User user, Boolean... additionalApprovalCondition) {
        checkAuth(objectOwnerKnowable, user, additionalApprovalCondition);
    }

    public void checkReadRight(ObjectOwnerKnowable objectOwnerKnowable, User user, Boolean... additionalApprovalCondition) {
        checkAuth(objectOwnerKnowable, user, additionalApprovalCondition);
    }

    public void checkUpdateRight(ObjectOwnerKnowable objectOwnerKnowable, User user, Boolean... additionalApprovalCondition) {
        checkAuth(objectOwnerKnowable, user, additionalApprovalCondition);
    }

    public void checkDeleteRight(ObjectOwnerKnowable objectOwnerKnowable, User user, Boolean... additionalApprovalCondition) {
        checkAuth(objectOwnerKnowable, user, additionalApprovalCondition);
    }

    public boolean isObjectOwner(ObjectOwnerKnowable objectOwnerKnowable, User user) {
        return objectOwnerKnowable.ownerOfObject().equals(user);
    }

    private void checkAuth(ObjectOwnerKnowable objectOwnerKnowable, User user, Boolean[] additionalApprovalCondition) {
        Boolean additionalConditionFlag = Arrays.asList(additionalApprovalCondition).stream().filter(condition -> condition.equals(true)).findAny().isPresent();
        rightCheck(objectOwnerKnowable.ownerOfObject().equals(user) || additionalConditionFlag);
    }
}