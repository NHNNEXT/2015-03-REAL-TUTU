angular.module('clientApp')
  .filter('contentGroup', function () {
    return function (contents, groupName) {
      if (!contents)
        return;
      if (!groupName)
        return contents;
      var result = [];
      contents.forEach(function (content) {
        if (content.contentGroup.name !== groupName)
          return;
        result.push(content);
      });
      return result;
    };
  })
  .controller('contentListController',function() {
    return function ($scope) {

      $scope.select = {};

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
      controller: "contentListController"
    };
  });
