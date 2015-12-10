angular.module('clientApp')
  .directive('lectureSummary', function () {
    return {
      restrict: 'E',
      scope: {
        lecture: '=', start: '=', end: '='
      },
      templateUrl: '/lecture.directive.lecture-summary/lecture-summary.html',
      /* @ngInject */
      controller: function (Content, $scope) {
        $scope.$watch('start', update, true);
        $scope.$watch('end', update, true);
        var now = new Date();
        var day = 24 * 60 * 60 * 1000;

        function update() {
          if (!$scope.start || !$scope.end)
            return;
          Content.getList({
            lectureId: $scope.lecture.id,
            start: $scope.start.getTime(),
            end: $scope.end.getTime()
          }).then(function (result) {
            $scope.divider = 3;
            $scope.contents = result;
            $scope.divider = $scope.contents.length;
            $scope.contents.forEach(function (content) {
              content.row = 1;
              content.col = 1;
              if (Math.abs(content.writeDate - now) < day)
                content.row++;
              if (Math.abs(content.startTime - now) < day)
                content.col++;
              if (Math.abs(content.endTime - now) < day)
                content.col++;
            });
          });

          $scope.dates = [];
          $scope.dates.push(new Date($scope.start.getTime() + (($scope.end - $scope.start) / 100 * 25)));
          $scope.dates.push(new Date($scope.start.getTime() + (($scope.end - $scope.start) / 100 * 50)));
          $scope.dates.push(new Date($scope.start.getTime() + (($scope.end - $scope.start) / 100 * 75)));
          $scope.now = {left: calculateLeft(new Date())};
        }

        $scope.class = function (content) {
          var classes = {SUBMIT: 'green', GENERAL: 'yellow', SCHEDULE: 'blue', NOTICE: 'purple'};
          if (!content.hover) {
            return classes[content.contentGroup.contentType];
          }
          return classes[content.contentGroup.contentType] + ' hover';
        };

        $scope.nowCovered = function () {
          return $scope.start < now && now < $scope.end;
        };

        $scope.left = function (date, index) {
          var style = {};
          style.left = calculateLeft(date);
          style.position = 'absolute';
          if (index !== undefined)
            style.top = (100 / $scope.divider) * (index % $scope.divider) + '%';
          return style;
        };

        $scope.range = function (content, index) {
          var style = {};
          style.left = calculateLeft(content.startTime);
          style.width = calculateLeft(content.endTime - content.startTime);
          style.position = 'absolute';
          style.top = (100 / $scope.divider) * (index % $scope.divider) + '%';
          return style;
        };


        function calculateLeft(date) {
          var left = (((date - $scope.start) / ($scope.end - $scope.start)) * 100);
          if (left < 0)
            return 0;
          if (left > 97)
            return '97%';
          return left + '%';
        }

      }
    };
  });
