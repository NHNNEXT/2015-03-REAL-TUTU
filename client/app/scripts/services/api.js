'use strict';

/**
 * @ngdoc service
 * @name clientApp.api
 * @description
 * # api
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('api', function ($http) {
    $http.get = function (url, data) {
      if (data !== undefined)
        url += "?" + parse(data);
      function parse(obj) {
        var str = [];
        for (var p in obj)
          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        return str.join("&");
      }

      return $http({method: "GET", url: url});
    };

    this.user = {};
    this.user.login = function () {

    };

  });
