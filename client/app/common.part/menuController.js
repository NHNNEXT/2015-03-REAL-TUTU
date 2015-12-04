angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope, dialog, rootUser, $mdSidenav) {
      $scope.login = dialog.login;
      $scope.register = dialog.register;
      $scope.rootUser = rootUser;
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
