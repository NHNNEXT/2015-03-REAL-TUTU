angular.module('clientApp')
  .controller('menuController',
  /* @ngInject */
  function ($scope, dialog, rootUser) {
    $scope.login = dialog.login;
    $scope.register = dialog.register;
    $scope.rootUser = rootUser;
  });
