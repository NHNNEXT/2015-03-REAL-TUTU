angular.module('clientApp')
  .directive('submitStatus', function () {
    return {
      restrict: 'E',
      scope: {
        submitRequires: '=',
        deadline: '=',
        select: '='
      },
      templateUrl: '/submit.directive.submit-status/submit-status.html',
      /* @ngInject */
      controller: function ($scope) {
        var names = $scope.names = {};
        names.NO_SUBMIT = "미제출";
        names.SUBMIT_IN_TIME = "정시 제출";
        names.SUBMIT_DELAYED = "지연 제출";
        $scope.$watch('submitRequires', function (submitRequires) {
          if (!submitRequires)
            return;
          $scope.NO_SUBMIT = 0;
          $scope.SUBMIT_IN_TIME = 0;
          $scope.SUBMIT_DELAYED = 0;
          submitRequires.forEach(function (submitRequire) {
            $scope[submitRequire.submitState]++;
          });
          var all = submitRequires.length;
          $scope.NO_SUBMIT_PERCENT = ($scope.NO_SUBMIT / all) * 100;
          $scope.SUBMIT_IN_TIME_PERCENT = ($scope.SUBMIT_IN_TIME / all) * 100;
          $scope.SUBMIT_DELAYED_PERCENT = ($scope.SUBMIT_DELAYED / all) * 100;
        });
      }
    };
  });
