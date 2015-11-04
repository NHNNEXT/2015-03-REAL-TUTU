angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '='
      },
      templateUrl: '/content/directive/content-list/content-list.html',
      controller: function ($scope, types) {
        $scope.$watch('contents', function (contents) {
          if (!contents)
            return;
          contents.forEach(function (content) {
            content.writeDate = new Date(content.writeDate);
            content.dueDate = new Date(content.dueDate);
          });
        });

        $scope.icons = types.contentIcons;

      }
    };
  });
