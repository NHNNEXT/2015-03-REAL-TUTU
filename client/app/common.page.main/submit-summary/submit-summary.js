angular.module('clientApp')
  .directive('submitSummary', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/submit-summary/submit-summary.html",
      /* @ngInject */
      controller: function ($scope, Content, rootUser) {
        $scope.page = 0;

        $scope.contents = [];

        $scope.getMore = function () {
          Content.getListInMyLectures({
            contentType: "SUBMIT",
            writer: rootUser.id,
            page: $scope.page
          }).then(function (contents) {
            $scope.more = contents.length === 10;
            $scope.page++;
            contents.forEach(function (content) {
              $scope.contents.push(content);
            });
          });
        };

        $scope.getMore();
      }
    };
  });
