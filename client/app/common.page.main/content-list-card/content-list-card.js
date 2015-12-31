angular.module('clientApp')
  .directive('contentListCard', function () {
    return {
      restrict: 'E',
      scope: {query: '=', head: "@"},
      templateUrl: "/common.page.main/content-list-card/content-list-card.html",
      /* @ngInject */
      controller: function ($scope, Content, rootUser, $rootScope, Page, $element) {
        var page = $scope.page = new Page();

        $scope.contents = [];

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          var query = {};
          angular.copy($scope.query, query);
          query.page = page.next();
          Content.getListInMyLectures(query).then(function (contents) {
            page.return(contents.length);
            contents.forEach(function (content) {
              $scope.contents.push(content);
            });
            if ($scope.contents.length === 0)
              $($element).remove();
          });
        };

        $scope.getMore();

        $rootScope.$on('userStateChange', $scope.getMore);
      }
    };
  });
