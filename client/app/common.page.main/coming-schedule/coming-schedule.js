angular.module('clientApp')
  .directive('comingSchedule', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/coming-schedule/coming-schedule.html",
      /* @ngInject */
      controller: function ($scope, http, Content, $rootScope, rootUser) {

        $scope.now = new Date();

        $scope.page = 0;

        $scope.contents = [];

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          http.get('/api/v1/content/list/coming', {page: $scope.page}).then(function (results) {
            results.forEach(function (result) {
              $scope.contents.push(new Content(result));
            });
            $scope.more = results.length === 10;
            $scope.page++;
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
