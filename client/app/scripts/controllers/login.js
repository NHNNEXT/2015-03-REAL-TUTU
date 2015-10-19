'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('LoginCtrl', function ($scope, regex, user, api, alert) {
    $scope.regex = regex;
    $scope.user = user;
    $scope.valid = function () {
      if (!$scope.ngRegex)
        return;
      return $scope.ngRegex['user.email'] && $scope.ngRegex['user.password'];
    };

    $scope.login = function (user) {
      if (!$scope.valid()) {
        alert('이메일과 패스워드를 입력해 주세요');
        return;
      }
      api.user.login(user);
    };
  });
