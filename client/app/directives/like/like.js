angular.module('clientApp')
  .directive('like', function () {
    return {
      restrict: 'E',
      scope: {
        value: '=',
        target: '='
      },
      templateUrl: '/directives/like/like.html',
      controller: function ($scope) {

      }
    };
  });
