angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'A',
      scope: {
        content: '=',
        tagClick: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html'
    };
  });
