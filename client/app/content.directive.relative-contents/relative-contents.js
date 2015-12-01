angular.module('clientApp')
  .directive('relativeContents', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        readonly: '='
      },
      templateUrl: '/content.directive.relative-contents/relative-contents.html'
    };
  });
