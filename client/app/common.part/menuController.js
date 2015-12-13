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
      vm.scrollWidth = function() {
        var outer = document.createElement("div");
        outer.style.visibility = "hidden";
        outer.style.width = "100px";
        outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps

        document.body.appendChild(outer);

        var widthNoScroll = outer.offsetWidth;
        // force scrollbars
        outer.style.overflow = "scroll";

        // add innerdiv
        var inner = document.createElement("div");
        inner.style.width = "100%";
        outer.appendChild(inner);

        var widthWithScroll = inner.offsetWidth;

        // remove divs
        outer.parentNode.removeChild(outer);

        return widthNoScroll - widthWithScroll;
      };

      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
