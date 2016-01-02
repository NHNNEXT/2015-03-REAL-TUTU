angular.module('clientApp')
  .directive('lectureSummary', function () {
    return {
      restrict: 'E',
      scope: {
        start: '=', end: '=', lectures: '=', lecture: '='
      },
      templateUrl: '/lecture.directive.lecture-summary/lecture-summary.html',
      /* @ngInject */
      controller: function (Content, $scope, $timeout, rootUser) {

        $timeout(function () {
          $scope.$watch(function () {
            return [$scope.start, $scope.end];
          }, update, true);
        }, 300);
        var now = new Date();
        var day = 24 * 60 * 60 * 1000;

        function update() {
          if (!$scope.start || !$scope.end)
            return;
          $scope.min = new Date($scope.end.getTime() - (day * 180));
          $scope.max = new Date($scope.start.getTime() + (day * 180));
          $scope.contents = [];
          if ($scope.lecture) {
            getLectureSchedule($scope.lecture.id);
            return;
          }
          if (!rootUser.lectures)
            return;
          rootUser.lectures.forEach(function (lecture) { //[TODO]로직상 맞지 않지만 임시로 남겨둠.
            if (!lecture.sideMenu)
              return;
            getLectureSchedule(lecture.id);
          });
        }

        function getLectureSchedule(lectureId) {
          Content.getListByDuration({
            lectureId: lectureId,
            start: $scope.start.getTime(),
            end: $scope.end.getTime()
          }).then(function (result) {
            result.forEach(function (content) {
              content.row = 1;
              content.col = 1;
              if (Math.abs(content.writeDate - now) < day)
                content.row++;
              if (Math.abs(content.startTime - now) < day)
                content.col++;
              if (Math.abs(content.endTime - now) < day)
                content.col++;
              $scope.contents.push(content);
            });
          });
        }

        $scope.class = function (content) {
          var classes = {SUBMIT: 'green', GENERAL: 'yellow', SCHEDULE: 'blue', NOTICE: 'purple'};
          if (!content.hover) {
            return classes[content.contentGroup.contentType];
          }
          return classes[content.contentGroup.contentType] + ' hover';
        };


      }
    };
  });
