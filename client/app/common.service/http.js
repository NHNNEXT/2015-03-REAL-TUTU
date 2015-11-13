angular.module('clientApp')
  /* @ngInject */
  .factory('http', function ($http, $q, responseCode, rootUser, dialog, alert) {
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
            if (obj[p] === undefined || typeof obj[p] === "function" || obj[p] === null || obj[p] === "null")
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
        switch (response.code) {
          case responseCode.SUCCESS:
            success(response.result);
            break;
          case responseCode.UNAUTHORIZED_REQUEST:
            alert.warning("권한이 없습니다.");
            break;
          case responseCode.LOGIN_NEEDED:
            alert.warning("로그인이 필요한 서비스입니다.");
            dialog.login();
            break;
          case responseCode.WROING_ACCESS:
            alert.warning("뚜찌빠찌뽀찌 뚜찌빠찌");
            dialog.login();
            break;
          default:
            error(response);
            break;
        }

      }).error(function (e) {
        if (!error)
          return;
        error(e);
      });
    };
    http.get = function (url, params, loginNeeded) {
      return $q(function (resolve, reject) {
        if (loginNeeded && !rootUser.loginCheck())
          return;
        http("GET", url, params, resolve, reject);
      });
    };
    http.post = function (url, params, loginNeeded) {
      return $q(function (resolve, reject) {
        if (loginNeeded && !rootUser.loginCheck())
          return;
        http("POST", url, params, resolve, reject);
      });
    };
    http.put = function (url, params, loginNeeded) {
      return $q(function (resolve, reject) {
        if (loginNeeded && !rootUser.loginCheck())
          return;
        http("PUT", url, params, resolve, reject);
      });
    };
    http.delete = function (url, params, loginNeeded) {
      return $q(function (resolve, reject) {
        if (loginNeeded && !rootUser.loginCheck())
          return;
        http("DELETE", url, params, resolve, reject);
      });
    };
    return http;
  });
