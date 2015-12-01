angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        keyword: '='
      },
      templateUrl: '/content.directive.content-list/content-list.html'
    };
  });
