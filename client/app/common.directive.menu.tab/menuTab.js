/**
 * Created by itmnext13 on 2015. 12. 13..
 */
angular.module('clientApp')
  .directive('sidenavFixed', function (rootUser,$state,mainButler) {
    return {
      restrict:'E',
      replace: true,
      templateUrl: '/common.part/main-sidenav-fixed.html',
      link: function(scope,element,attr) {
        scope.vm = {};
        var vm = scope.vm;
        var scrollWidth = (function() {
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
        })();

        vm.rootUser = rootUser;
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

        vm.scrollWidth = scrollWidth;

        element.bind('mouseenter',function() {
          element.width(element.width()-vm.scrollWidth) ;
        });
        element.bind('mouseleave',function() {
          element.width(element.width()+vm.scrollWidth);
        });
      }
    };
  });
