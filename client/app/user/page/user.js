angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($scope, regex, user, alert, userBroker) {
    $scope.regex = regex;
    $scope.user = user;
    $scope.valid = function () {
      if (!$scope.ngRegex)
        return;
      return $scope.ngRegex['user.email'] && $scope.ngRegex['user.password'];
    };

    $scope.login = function (user) {
      if (!$scope.valid()) {
        alert.warning('이메일과 패스워드를 입력해 주세요');
        return;
      }
      userBroker.login(user);
    };

    $scope.register = function (user) {
      if (!$scope.valid()) {
        alert.warning('정보를 입력해주세요.');
        return;
      }
      if (!$scope.ngRegex['user.name']) {
        alert.warning('정보를 입력해주세요.');
        return;
      }
      userBroker.register(user);
    };
  };

  $stateProvider
    .state('login', {
      url: "/login",
      templateUrl: "/user/page/login.html",
      controller: controller,
      uiViewClass:'full-height bg',
      headerClass:'navbar-fixed-top navbar-transparent'
    })
    .state('register', {
      url: "/register",
      templateUrl: "/user/page/register.html",
      controller: controller,
      uiViewClass:'full-height bg',
      headerClass:'navbar-fixed-top navbar-transparent'
    })
});
