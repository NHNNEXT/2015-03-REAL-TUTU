angular.module('clientApp')
  .directive('contentHeader', function (Now) {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        index: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html',
      link: function (s) {
        s.now = Now;
      }
    };
  });
