angular.module('clientApp')
  .directive('notice', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/notice/notice.html",
      /* @ngInject */
      controller: function ($scope, Content, rootUser, $rootScope, Page) {
        var page = $scope.page = new Page();

        $scope.contents = [];

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          Content.getListInMyLectures({contentType: "NOTICE", page: page.next()}).then(function (contents) {
            page.return(contents.length);
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
