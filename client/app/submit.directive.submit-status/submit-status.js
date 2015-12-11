angular.module('clientApp')
  .directive('submitStatus', function () {
    return {
      restrict: 'E',
      scope: {
        submitRequiredUsers: '=',
        deadline: '='
      },
      templateUrl: '/submit.directive.submit-status/submit-status.html',
      /* @ngInject */
      controller: function ($scope) {

        $scope.left = function (date) {
          var style = {};
          style.left = calculateLeft(date);
          style.height = '100%';
          style.position = 'absolute';
          return style;
        };

        var day = 24 * 60 * 60 * 1000;
        $scope.$watch('deadline', function (deadline) {
          if (!deadline)
            return;
          var dates = $scope.dates = [];
          for (var i = -4; i < 4; i++) {
            var date = new Date(deadline);
            date.setDate(date.getDate() + i);
            dates.push(date);
          }
          var start = $scope.start = new Date(deadline);
          start.setDate(start.getDate() - 5);
          start.setHours(0);
          start.setMinutes(0);
          start.setMilliseconds(0);
          $scope.deadlineStyle = {};
          $scope.deadlineStyle.left = calculateLeft(deadline);
        });

        $scope.$watch('submitRequiredUsers', function (users) {
          if (!users)
            return;
          users.forEach(function (user) {
            if (!user.submits || user.submits.length === 0)
              return;
            user.mySubmits = [];
            user.submits.forEach(function (submit) {
              if (submit.writer.id !== user.user.id)
                return;
              user.mySubmits.push(submit);
            });
            user.submitted = user.mySubmits.find(function (submit) {
              return submit.writeDate < $scope.deadline;
            });
          });
        }, true);

        function calculateLeft(date) {
          var left = 10 + (((date - $scope.start) / (day * 9)) * 100);
          if (left < 10)
            left = 10;
          return left + '%';
        }
      }
    };
  });
