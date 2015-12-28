angular.module('clientApp')
  .directive('contentListCard', function () {
    return {
      restrict: 'E',
      scope: {query: '=', head: "@"},
      templateUrl: "/common.page.main/content-list-card/content-list-card.html",
      /* @ngInject */
      link: function (s, e) {
        s.el = e;
      },
      controller: function ($scope, Content, rootUser, $rootScope, Page) {
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
              $($scope.el).remove();
          });
        };

        $scope.getMore();
        $rootScope.$on('userStateChange', $scope.getMore);
      }
    };
  });
