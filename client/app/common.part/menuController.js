angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope, rootUser,$mdSidenav,$state, Message, mainButler) {

      $scope.rootUser = rootUser;
      $scope.Message = Message;

      var vm = $scope;
      vm.date = mainButler.date;

      vm.setDateNow = function() {
        mainButler.setDateNow();
        vm.date = mainButler.date;
        if($state.is("main")) {
          $state.reload("main");
        } else {
          $state.go("main");
        }
      };

      vm.setDateOneWeek = function() {
        mainButler.setDateOneWeek();
        vm.date = mainButler.date;
        if($state.is("main")) {
          $state.reload("main");
        }else {
          $state.go("main");
        }
      };
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
