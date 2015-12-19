angular.module('clientApp')
  .directive('contentTimeline', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        start: '=',
        end: '=',
        height: '@'
      },
      templateUrl: '/content.directive.content-timeline/content-timeline.html',
      controller: function ($scope) {
        $scope.body = {
          height: $scope.height
        };


        if (!$scope.start || !$scope.end) {
          var start = $scope.start = new Date();
          var end = $scope.end = new Date();
          start.setDate(start.getDate() - 7);
          end.setDate(end.getDate() + 3);
        }

        $scope.$watch(function () {
          return [$scope.start, $scope.end, $scope.contents];
        }, function () {
          $scope.weeks = [];
          /* jshint ignore:start */
          var end = new Date($scope.end);
          end.setDate($scope.end.getDate() + 1);
          var start = new Date($scope.start);
          var biggerThenWeek = (end - start) > 24 * 1000 * 60 * 60 * 7;
          if (biggerThenWeek) {
            start.setDate(start.getDate() - start.getDay());
            if (end.getDay() !== 0)
              end.setDate(end.getDate() + (7 - end.getDay()));
          }
          var week = [];
          for (var date = new Date(start); date < end; date.setDate(date.getDate() + 1)) {
            var day = {date: new Date(date), events: []};
            if ($scope.contents)
              $scope.contents.forEach(function (content) {
                if (isDay(date, content))
                  day.events.push(content);
              });
            week.push(day);
            if (date.getDay() === 6 && biggerThenWeek) {
              $scope.weeks.push(week);
              week = [];
            }
          }
          if (week.length)
            $scope.weeks.push(week);
          function isDay(day, content) {
            if (day.isSameDay(content.startTime) || day.isSameDay(content.endTime))
              return true;
            return day > content.startTime && day < content.endTime;
          }

          /* jshint ignore:end */
        }, true);

      }
    };
  });
