angular.module('clientApp')
  .directive('nxNavFixed', function ($timeout,rootUser,$state,mainButler,containerRepository) {
    return {
      restrict:'E',
      replace: true,
      templateUrl: '/common.part/main-nav-fixed.html',
      link: function(scope,element) {
        scope.vm = {};

        var vm = scope.vm;
        vm.rootUser = rootUser;
        vm.containerRepository = containerRepository;
        vm.scrollWidth = mainButler.scrollWidth;
        vm.date = mainButler.date;

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

        element.bind('mouseenter',function() {
          element.width(element.width()-vm.scrollWidth) ;
        });
        element.bind('mouseleave',function() {
          element.width(element.width()+vm.scrollWidth);
        });

      }
    };
  });
