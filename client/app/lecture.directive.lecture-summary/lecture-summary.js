angular.module('clientApp')
  .directive('lectureSummary', function () {
    return {
      restrict: 'E',
      scope: {
        lecture: '='
      },
      templateUrl: '/lecture.directive.lecture-summary/lecture-summary.html',
      /* @ngInject */
      controller: function (Content, $scope) {
        var start = new Date();
        var end = new Date();
        start.setDate(start.getDate() - 6);
        end.setDate(end.getDate() + 3);

        Content.getList({
          lectureId: $scope.lecture.id,
          start: start.getTime(),
          end: end.getTime()
        }).then(function (result) {
          var now = new Date();
          var day = 24 * 60 * 60 * 1000;
          $scope.contents = result;
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

        $scope.class = function (content) {
          var classes = {SUBMIT: 'green', GENERAL: 'yellow', SCHEDULE: 'blue', NOTICE: 'purple'};
          if (!content.hover) {
            return classes[content.contentGroup.contentType];
          }
          return classes[content.contentGroup.contentType] + ' hover';
        };


        $scope.dates = [];
        for (var date = new Date(start); date < end; date.setDate(date.getDate() + 1)) {
          $scope.dates.push(new Date(date));
        }

        var divider = 3;

        $scope.left = function (date, index) {
          var style = {};
          style.left = calculateLeft(date);
          style.height = '100%';
          style.position = 'absolute';
          if (index !== undefined)
            style.top = (100 / divider) * (index % divider) + '%';
          return style;
        };

        $scope.range = function (content, index) {
          var style = {};
          style.left = calculateLeft(content.startTime);
          style.width = calculateLeft(content.endTime - content.startTime);
          style.height = '2px';
          style.background = '#EEE';
          style.position = 'absolute';
          style.bottom = 0;
          style.top = (100 / divider) * (index % divider) + '%';
          return style;
        };

        function calculateLeft(date) {
          var left = 10 + (((date - start) / (end - start)) * 100);
          if (left < 10)
            left = 10;
          return left + '%';
        }

      }
    };
  });
