angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'A',
      scope: {
        content: '=',
        types: '='
      },
      templateUrl: '/content/content-header/content-header.html',
    };
  });
