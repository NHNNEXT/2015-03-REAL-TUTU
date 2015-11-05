angular.module('clientApp')
  .directive('freewall', function ($timeout, $window) {
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        var wall = new freewall(element);
        scope.$watch(function () {
          $timeout(function () {
            wall.fitWidth();
          });
        }, true);

        angular.element($window).bind('resize', function () {
          $timeout(function () {
            wall.fitWidth();
          });
        });

      }
    };
  });
