/* @ngInject */
angular.module('clientApp').controller('authController', function ($scope, rootUser, alert, dialog, responseCode) {
  $scope.rootUser = rootUser;

  $scope.login = function (user) {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.login();
  };

  $scope.register = function (user) {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.register();
  };

});

