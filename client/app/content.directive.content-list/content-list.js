angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        query: '='
      },
      templateUrl: '/content.directive.content-list/content-list.html',
      controller: function ($scope, Content, Loading) {
        $scope.page = 0;

        $scope.$watch('page', function (page) {
          if (page === undefined)
            return;
          getContents();
        });

        $scope.$watch('query', function (query) {
          if (query === undefined)
            return;
          getContents();
          Content.getListSize(query).then(function (size) {
            $scope.size = size;
            definePages();
          });
        }, true);

        var pageSize = 10;

        $scope.setPage = function (page) {
          $scope.page = page;
        };

        function definePages() {
          $scope.pages = [];
          for (var i = 0; i < $scope.size; i += pageSize) {
            $scope.pages.push(i);
          }
        }

        function getContents() {
          var q = {};
          Loading.start();
          angular.copy($scope.query, q);
          q.page = $scope.page;
          Content.getList(q).then(function (contents) {
            Loading.end();
            $scope.contents = contents;
          });
        }
      }
    };
  });
