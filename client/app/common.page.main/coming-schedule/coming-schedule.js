angular.module('clientApp')
  .directive('comingSchedule', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/coming-schedule/coming-schedule.html",
      link: function (s, e) {
        s.el = e;
      },
      /* @ngInject */
      controller: function ($scope, http, Content, $rootScope, rootUser, Page) {

        $scope.now = new Date();

        var page = $scope.page = new Page();

        $scope.contents = [];

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          http.get('/api/v1/content/list/coming', {page: page.next()}).then(function (results) {

            page.return(results.length);
            results.forEach(function (result) {
              $scope.contents.push(new Content(result));
            });
            if ($scope.contents.length === 0)
              $($scope.el).remove();
          });
        };

        $scope.getMore();

        $rootScope.$on('userStateChange', $scope.getMore);

        $scope.style = function (index) {
          return {"fontSize": (100 - index * 15) + '%'};
        };

      }
    };
  });
