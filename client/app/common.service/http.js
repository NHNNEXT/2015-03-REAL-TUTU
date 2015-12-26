angular.module('clientApp')
  /* @ngInject */
  .factory('http', function ($http, $q, responseCode, dialog, alert, $state, emoticon) {
    var http = function (method, url, params, success, error, json) {
      var options = {
        method: method, url: url
      };
      if (json)
        options.headers = {'Content-Type': 'application/json'};
      else
        options.headers = {'Content-Type': 'application/x-www-form-urlencoded'};

      if (method === "GET" || method === "DELETE")
        options.params = params;
      else if (json)
        options.data = params;
      else {
        options.transformRequest = function (obj) {
          var str = [];
          for (var p in obj) {
            if (obj[p] === undefined || obj[p] === "" || typeof obj[p] === "function" || obj[p] === null || obj[p] === "null")
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
            $state.go('main');
            break;
          case responseCode.PATTERN_NOT_MATCHED:
            alert.warning(response.result === undefined ? "유효하지 않은 입력입니다." : response.result);
            break;
          case responseCode.LOGIN_NEEDED:
            alert.warning("로그인이 필요한 서비스입니다.");
            dialog.login();
            break;
          case responseCode.WRONG_ACCESS:
            emoticon.dontDoThat();
            $state.go('main');
            break;
          case responseCode.RESOURCE_NOT_EXIST:
            $state.go('notfound');
            break;
          case responseCode.DATA_INTEGRITY_ERROR:
            alert.warning(response.result === undefined ? "DB 구조와 달라 에러가 발생했습니다." : response.result);
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
    http.get = function (url, params) {
      return $q(function (resolve, reject) {
        http("GET", url, params, resolve, reject);
      });
    };
    http.post = function (url, params, json) {
      return $q(function (resolve, reject) {
        http("POST", url, params, resolve, reject, json);
      });
    };
    http.put = function (url, params, json) {
      return $q(function (resolve, reject) {
        http("PUT", url, params, resolve, reject, json);
      });
    };
    http.delete = function (url, params) {
      return $q(function (resolve, reject) {
        http("DELETE", url, params, resolve, reject);
      });
    };
    return http;
  });
