angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        query: '='
      },
      templateUrl: '/content.directive.content-list/content-list.html',
      controller: function ($scope, Content, Loading, $stateParams) {

        var pageSize = 10;
        if (!$scope.query)
          $scope.query = {};
        $scope.query.contentGroupName = $stateParams.contentGroupName;
        $scope.query.lectureId = $stateParams.id;

        $scope.pageRequest = function (page) {
          getContents($scope.query, page);
        };

        $scope.$watch('query', function (query) {
          if (query === undefined)
            return;
          getContents(query);
          Content.getListSize(query).then(function (size) {
            definePages(size);
          });
        }, true);

        function definePages(size) {
          $scope.pages = [];
          for (var i = 0; i < size; i += pageSize) {
            $scope.pages.push(i);
          }
        }

        function getContents(query, page) {
          Loading.start();
          var q = {};
          angular.copy(query, q);
          if (!page)
            page = 0;
          $scope.page = page;
          q.page = page;
          Content.getList(q).then(function (contents) {
            Loading.end();
            $scope.contents = contents;
          });
        }
      }
    };
  });
