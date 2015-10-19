'use strict';

/**
 * @ngdoc service
 * @name clientApp.api
 * @description
 * # api
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('api', function ($http, alert) {
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
    this.user.login = function (user) {
      if (!user)
        return;
      $http.post('/api/v1/user/login', user).success(function (res) {
        if (res.err) {
          alert(res.err);
          return;
        }
        user.logged = true;
      });
    };

  });
