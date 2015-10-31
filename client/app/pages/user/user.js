angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($scope, regex, user, alert, http) {
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
      http.post('/api/v1/user/login', user, function (result) {
        angular.copy(user, result);
        user.logged = true;
      });
    };

    $scope.register = function (user) {
      if (!$scope.valid()) {
        alert('이메일과 패스워드를 입력해 주세요');
        return;
      }
      http.post('/api/v1/user', user, function (result) {
        angular.copy(user, result);
        user.logged = true;
      });
    };

  };

  $stateProvider
    .state('login', {
      url: "/login",
      templateUrl: "/pages/user/login.html",
      controller: controller
    })
    .state('register', {
      url: "/register",
      templateUrl: "/pages/user/register.html",
      controller: controller
    })
});
