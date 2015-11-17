angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        query: '=',
        keyword: '='
      },
      templateUrl: '/content.directive.content-list/content-list.html',
      /* @ngInject */
      controller: function ($scope) {
        $scope.$watch('contents', function (contents) {
          if (!contents)
            return;
          contents.forEach(function (content) {
            content.writeDate = new Date(content.writeDate);
            content.dueDate = new Date(content.dueDate);
          });
        });
      }
    };
  });
