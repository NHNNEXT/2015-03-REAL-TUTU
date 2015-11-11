angular.module('clientApp')
  .controller('sideController', function ($scope, dialog, user) {
    $scope.login = dialog.login;
    $scope.register = dialog.register;
    $scope.user = user;
  });
