angular.module('clientApp')
  .service('responseCode', function () {
    /* @ngInject */
  }).run(function ($http, responseCode) {
  var code = {
    "UNAUTHORIZED_REQUEST": 100,
    "SUCCESS": 0,
    "PATTERN_NOT_MATCHED": 101,
    "Node": {
      "CHILD_EXIST": 39
    },
    "Enroll": {
      "WAITING_FOR_APPROVAL": 16
    },
    "ContentRelation": {
      "CANT_BIND_SELF": 30,
      "ALREADY_EXIST": 29
    },
    "Login": {
      "WITHDRAWAL_ACCOUNT": 5,
      "WRONG_PASSWORD": 4,
      "DO_EMAIL_VERIFY_FIRST": 40,
      "NOT_EXIST_EMAIL": 3
    },
    "RESOURCE_NOT_EXIST": 299,
    "GetSessionUser": {
      "EMPTY": 6
    },
    "MailRequest": {
      "USER_NOT_EXIST": 44,
      "USER_ALREADY_VERIFIED": 47,
      "ALREADY_PASSWORD_CHANGE_MAIL_SENT": 43
    },
    "LOGIN_NEEDED": 13,
    "Like": {
      "LECTURE": 10,
      "ADD": 14,
      "REMOVE": 15,
      "CONTENT": 9,
      "REPLY": 11
    },
    "WRONG_ACCESS": 1,
    "PasswordChange": {
      "KEY_NOT_MATCHED": 239,
      "EXPIRED": 216,
      "TOO_MANY_TRY": 215
    },
    "Register": {
      "ALREADY_EXIST_EMAIL": 2,
      "ACCOUNT_IS_WAITING_FOR_EMAIL_CERTIFY": 45
    },
    "DATA_INTEGRITY_ERROR": 199,
    "UserAuth": {
      "KEY_NOT_MATCHED": 229,
      "MAIL_EXPIRED": 214,
      "USER_ALREADY_VERIFIED": 230
    },
    "FileUpload": {
      "ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT": 8,
      "FILE_NOT_ATTACHED": 7
    }
  };
  angular.copy(code, responseCode);
});
