angular.module('clientApp')
  .directive('notice', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/notice/notice.html",
      /* @ngInject */
      controller: function ($scope, Content, rootUser, $rootScope) {
        $scope.page = 0;

        $scope.contents = [];

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          Content.getListInMyLectures({contentType: "NOTICE", page: $scope.page}).then(function (contents) {
            $scope.more = contents.length === 10;
            $scope.page++;
            contents.forEach(function (content) {
              $scope.contents.push(content);
            });
          });
        };

        $scope.getMore();
        $rootScope.$on('userStateChange', $scope.getMore);
      }
    };
  });
