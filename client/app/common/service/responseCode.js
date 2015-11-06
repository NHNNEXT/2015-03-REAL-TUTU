angular.module('clientApp')
  .service('responseCode', function () {
  }).run(function ($http, responseCode) {
    $http.get('/api/v1/responseCode').success(function (res) {
      angular.copy(res, responseCode);
    });
  });
