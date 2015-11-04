angular.module('clientApp')
  .directive('freewall', function () {
    return {
      restrict: 'E',
      templateUrl: '/common/directive/freewall/freewall.html',
      link: function postLink(scope, element) {
        var wall = new freewall(element);
        scope.$watch('items', function () {
          wall.fitWidth();
        }, true);
      }
    };
  });
