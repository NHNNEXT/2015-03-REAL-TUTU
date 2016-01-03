angular.module('clientApp')
  .service('responseCode', function () {
    /* @ngInject */
  }).run(function ($http, responseCode) {
  var code = {
    "UNAUTHORIZED_REQUEST": 3,
    "SUCCESS": 0,
    "PATTERN_NOT_MATCHED": 4,
    "Node": {
      "CHILD_EXIST": 28
    },
    "Enroll": {
      "WAITING_FOR_APPROVAL": 25
    },
    "ContentRelation": {
      "UPDATE_BLOCKED": 36,
      "CANT_BIND_SELF": 27,
      "ALREADY_EXIST": 26
    },
    "Login": {
      "WITHDRAWAL_ACCOUNT": 12,
      "WRONG_PASSWORD": 11,
      "DO_EMAIL_VERIFY_FIRST": 9,
      "NOT_EXIST_EMAIL": 10
    },
    "RESOURCE_NOT_EXIST": 6,
    "GetSessionUser": {
      "EMPTY": 17
    },
    "MailRequest": {
      "USER_NOT_EXIST": 13,
      "USER_ALREADY_VERIFIED": 14,
      "ALREADY_PASSWORD_CHANGE_MAIL_SENT": 15,
      "USER_NOT_VERIFIED": 16
    },
    "LOGIN_NEEDED": 2,
    "Like": {
      "LECTURE": 21,
      "ADD": 23,
      "REMOVE": 24,
      "CONTENT": 20,
      "REPLY": 22
    },
    "WRONG_ACCESS": 1,
    "PasswordChange": {
      "KEY_NOT_MATCHED": 33,
      "EXPIRED": 34,
      "TOO_MANY_TRY": 32
    },
    "Register": {
      "ALREADY_EXIST_EMAIL": 7,
      "ACCOUNT_IS_WAITING_FOR_EMAIL_CERTIFY": 8
    },
    "DATA_INTEGRITY_ERROR": 5,
    "Tag": {
      "UPDATE_BLOCKED": 35
    },
    "UserAuth": {
      "KEY_NOT_MATCHED": 30,
      "MAIL_EXPIRED": 29,
      "USER_ALREADY_VERIFIED": 31
    },
    "FileUpload": {
      "ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT": 19,
      "FILE_NOT_ATTACHED": 18
    }
  };
  angular.copy(code, responseCode);
});
