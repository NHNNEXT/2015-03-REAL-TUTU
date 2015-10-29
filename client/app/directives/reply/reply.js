angular.module('clientApp')
  .directive('reply', function ($compile, $state) {
    return {
      restrict: 'A',
      scope: {
        reply: '='
      },
      templateUrl: '/directives/reply/reply.html',
      controller: function ($scope) {
      }
    };
  });
