angular.module('clientApp')
  .controller('menuController',
  /* @ngInject */
  function ($scope, dialog, user, userBroker) {
    $scope.login = dialog.login;
    $scope.register = dialog.register;
    $scope.user = user;
    $scope.logout = userBroker.logout;
  });
