angular.module('clientApp')
  .filter('contentGroup', function () {
    return function (contents, contentGroups) {
      if (!contents)
        return;
      var result = [];
      contents.forEach(function (content) {
        if (!contentGroups[content.contentGroup.name].select)
          return;
        result.push(content);
      });
      return result;
    };
  })
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        keyword: '='
      },
      templateUrl: '/content.directive.content-list/content-list.html',
      controller: function ($scope) {
        $scope.tagClick = function (tag) {
          $scope.keyword = tag;
          $scope.searching = true;
        };

        $scope.getIcon = function () {
          if ($scope.searching)
            return "/resource/icon/ic_close.svg";
          return "/resource/icon/search.svg";
        };

        $scope.toggleSearching = function () {
          if ($scope.searching) {
            $scope.searching = false;
            $scope.keyword = "";
            return;
          }
          $scope.searching = true;
        };

        $scope.contentGroups = {};
        $scope.$watch('contents', function (contents) {
          if (!contents)
            return;
          contents.forEach(function (content) {
            $scope.contentGroups[content.contentGroup.name] = content.contentGroup;
            content.contentGroup.select = true;
          });
        });
      }
    };
  });
