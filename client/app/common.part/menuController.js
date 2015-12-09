angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope, dialog, rootUser, $mdSidenav, Message) {
      $scope.login = dialog.login;
      $scope.register = dialog.register;
      $scope.rootUser = rootUser;
      $scope.Message = Message;
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
