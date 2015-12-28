angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        index: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html',
      link: function (s) {
        s.now = new Date();
      }
    };
  });
