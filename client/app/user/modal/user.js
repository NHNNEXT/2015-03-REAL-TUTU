angular.module('clientApp').controller('userLoginController', function ($scope, regex, user, alert, userBroker) {
  $scope.regex = regex;
  $scope.user = user;
  $scope.valid = function () {
    if (!$scope.ngRegex)
      return;
    return $scope.ngRegex['user.email'] && $scope.ngRegex['user.password'];
  };

  $scope.validName = function () {
    if (!$scope.ngRegex)
      return;
    return $scope.ngRegex['user.name'];
  };

  $scope.login = function (user) {
    if (!$scope.valid()) {
      alert.warning('이메일과 패스워드를 입력해 주세요');
      return;
    }
    userBroker.login(user);
  };

  $scope.register = function (user) {
    if (!$scope.valid() || !$scope.validName()) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    userBroker.register(user);
  };
});

