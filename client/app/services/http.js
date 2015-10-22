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
      var options = {method: method, url: url};
      if (method === "GET" || method === "DELETE")
        options.params = params;
      else
        options.data = params;
      $http(options).success(function (res) {
        if (res.err) {
          alert(res.err);
          return;
        }
        if (!success)
          return;
        success(res.result);
      }).error(function (e) {
        if (!error)
          return;
        error(e);
      });
    };
    http.get = function (url, params, success, error) {
      this("GET", url, params, success, error);
    };
    http.post = function (url, params, success, error) {
      this("POST", url, params, success, error);
    };
    http.put = function (url, params, success, error) {
      this("PUT", url, params, success, error);
    };
    http.delete = function (url, params, success, error) {
      this("DELETE", url, params, success, error);
    };
    return http;
  });
