angular.module('clientApp')
  .directive('submitRequires', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/submit-requires/submit-requires.html",
      /* @ngInject */
      controller: function ($scope, http, $rootScope, rootUser, SubmitRequire, Page, $element) {
        var page = $scope.page = new Page();

        $scope.submitRequires = [];

        $scope.doneCount = function (submitRequires) {
          var done = 0;
          submitRequires.forEach(function (submitRequire) {
            if (submitRequire.done)
              done++;
          });
          return done;
        };

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          http.get('/api/v1/content/submit/require', {page: page.next()}).then(function (results) {
            page.return(results.length);
            results.forEach(function (result) {
              $scope.submitRequires.push(new SubmitRequire(result));
            });
            if ($scope.submitRequires.length === 0)
              $($element).remove();
          });
        };

        $scope.getMore();

        $rootScope.$on('userStateChange', $scope.getMore);

      }
    };
  });
