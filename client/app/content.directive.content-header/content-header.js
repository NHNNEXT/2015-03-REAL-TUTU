angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        tagClick: '=',
        index: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html'
    };
  });
