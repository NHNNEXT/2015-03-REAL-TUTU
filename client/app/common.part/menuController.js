angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope, rootUser,$mdSidenav,$state, Message, mainButler) {

      $scope.rootUser = rootUser;
      $scope.Message = Message;

      var vm = $scope;
      vm.date = mainButler.date;

      vm.setNowAndClose = function() {
        vm.setDateNow();
        vm.close();
      };
      vm.setDateNow = function() {
        mainButler.setDateNow();
        vm.date = mainButler.date;
        if($state.is("main")) {
          $state.reload("main");
        } else {
          $state.go("main");
        }
        return vm;
      };

      vm.setDateOneWeek = function() {
        mainButler.setDateOneWeek();
        vm.date = mainButler.date;
        if($state.is("main")) {
          $state.reload("main");
        }else {
          $state.go("main");
        }

        return vm;
      };
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
