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
      "Like": {
        "LECTURE": 10,
        "ADD": 14,
        "REMOVE": 15,
        "CONTENT": 9,
        "LESSON": 12,
        "REPLY": 11
      },
      "Register": {
        "ALREADY_EXIST_EMAIL": 2
      },
      "Login": {
        "WITHDRAWAL_ACCOUNT": 5,
        "WRONG_PASSWORD": 4,
        "NOT_EXIST_EMAIL": 3
      },
      "GetSessionUser": {
        "EMPTY": 6
      },
      "FileUpload": {
        "ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT": 8,
        "FILE_NOT_ATTACHED": 7
      },
      "WROING_ACCESS": 1,
      "LOGIN_NEEDED": 13
    };
    angular.copy(code, responseCode);
  });
