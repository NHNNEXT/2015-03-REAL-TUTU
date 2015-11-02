package org.next.infra.reponse;

public class ResponseCode {

    public static final Integer SUCCESS = 0;

    public static final class Register{
        public static final Integer ALREADY_EXIST_EMAIL = 1;
    }


    public static final class Login{
        public static final Integer NOT_EXIST_EMAIL = 1;
        public static final Integer WRONG_PASSWORD = 2;
        public static final Integer WITHDRAWAL_ACCOUNT = 3;
    }

    public static final class GetSessionUser {
        public static final Integer EMPTY = 1;
    }
}
