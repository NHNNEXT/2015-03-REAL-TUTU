angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope, rootUser, $mdSidenav, $state, Message) {

      $scope.rootUser = rootUser;
      $scope.Message = Message;
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
