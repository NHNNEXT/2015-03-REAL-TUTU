angular.module('clientApp')
  .service('responseCode', function () {
    /* @ngInject */
  }).run(function ($http, responseCode) {
  //$http.get('/api/v1/responseCode').success(function (res) {
  //  angular.copy(res, responseCode);
  //});
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
    "Term": {
      "ALREADY_EXIST": 35
    },
    "Login": {
      "USER_NOT_EXIST": 44,
      "WITHDRAWAL_ACCOUNT": 5,
      "WRONG_PASSWORD": 4,
      "DO_EMAIL_VERIFY_FIRST": 40,
      "NO_MORE_PASSWORD_CHANGE_TRY_COUNT": 42,
      "ALREADY_PASSWORD_CHANGE_MAIL_SENT": 43,
      "NOT_EXIST_EMAIL": 3
    },
    "GetSessionUser": {
      "EMPTY": 6
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
    "Register": {
      "ALREADY_EXIST_EMAIL": 2
    },
    "DATA_INTEGRITY_ERROR": 199,
    "FileUpload": {
      "ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT": 8,
      "FILE_NOT_ATTACHED": 7
    }
  };
  angular.copy(code, responseCode);
});
