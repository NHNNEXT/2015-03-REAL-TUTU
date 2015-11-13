angular.module('clientApp')
  .controller('menuController',
  /* @ngInject */
  function ($scope, dialog, rootUser, userBroker) {
    $scope.login = dialog.login;
    $scope.register = dialog.register;
    $scope.rootUser = rootUser;
    $scope.logout = userBroker.logout;
  });
