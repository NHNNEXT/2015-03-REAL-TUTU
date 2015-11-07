'use strict';

/**
 * @ngdoc service
 * @name clientApp.http
 * @description
 * # http
 * Factory in the clientApp.
 */
angular.module('clientApp')
  .factory('http', function ($http, $q, responseCode, user) {
    var http = function (method, url, params, success, error) {
      var options = {
        method: method, url: url,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      };
      if (method === "GET" || method === "DELETE")
        options.params = params;
      else {
        options.transformRequest = function (obj) {
          var str = [];
          for (var p in obj) {
            if (obj[p] === undefined || obj[p] === null || obj[p] === "null")
              continue;
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          }
          return str.join("&");
        };
        options.data = params;
      }

      $http(options).success(function (response) {
        if (!success)
          return;
        if (response.code === responseCode.SUCCESS) {
          success(response.result);
        }
        error(response);
      }).error(function (e) {
        if (!error)
          return;
        error(e);
      });
    };
    http.get = function (url, params, loginNeeded) {
      if (loginNeeded && !user.loginCheck())
        return;
      return $q(function (resolve, reject) {
        http("GET", url, params, resolve, reject);
      });
    };
    http.post = function (url, params, loginNeeded) {
      if (loginNeeded && !user.loginCheck())
        return;
      return $q(function (resolve, reject) {
        http("POST", url, params, resolve, reject);
      });
    };
    http.put = function (url, params, loginNeeded) {
      if (loginNeeded && !user.loginCheck())
        return;
      return $q(function (resolve, reject) {
        http("PUT", url, params, resolve, reject);
      });
    };
    http.delete = function (url, params, loginNeeded) {
      if (loginNeeded && !user.loginCheck())
        return;
      return $q(function (resolve, reject) {
        http("DELETE", url, params, resolve, reject);
      });
    };
    return http;
  });
