angular.module('clientApp')
  .service('responseCode', function () {
  }).run(function (http, responseCode) {
    http.get('/code.json', {}, function (res) {
      angular.copy(res, responseCode);
    });
    return responseCode;
  });
