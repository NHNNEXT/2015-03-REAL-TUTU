angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        index: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html'
    };
  });
