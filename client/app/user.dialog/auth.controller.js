/* @ngInject */
angular.module('clientApp').controller('authController',
  function ($scope, rootUser, alert) {
  $scope.rootUser = rootUser;

  $scope.login = function () {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.login();
  };

  $scope.register = function () {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.register();
  };

});


