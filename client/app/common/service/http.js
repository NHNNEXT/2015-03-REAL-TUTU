'use strict';

/**
 * @ngdoc service
 * @name clientApp.http
 * @description
 * # http
 * Factory in the clientApp.
 */
angular.module('clientApp')
  .factory('http', function ($http, alert) {
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

      $http(options).success(function (res) {
        if (!success)
          return;
        success(res);
      }).error(function (e) {
        if (!error)
          return;
        error(e);
      });
    };
    http.get = function (url, params, success, error) {
      if (typeof params === "function")
        this("GET", url, {}, params, success);
      this("GET", url, params, success, error);
    };
    http.post = function (url, params, success, error) {
      if (typeof params === "function")
        this("POST", url, {}, params, success);
      this("POST", url, params, success, error);
    };
    http.put = function (url, params, success, error) {
      if (typeof params === "function")
        this("PUT", url, {}, params, success);
      this("PUT", url, params, success, error);
    };
    http.delete = function (url, params, success, error) {
      if (typeof params === "function")
        this("DELETE", url, {}, params, success);
      this("DELETE", url, params, success, error);
    };
    return http;
  });
