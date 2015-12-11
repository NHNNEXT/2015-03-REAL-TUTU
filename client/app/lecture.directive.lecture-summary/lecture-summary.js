angular.module('clientApp')
  .directive('lectureSummary', function () {
    return {
      restrict: 'E',
      scope: {
        start: '=', end: '='
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

        $scope.lectures = rootUser.lectures;

        function update() {
          if (!$scope.start || !$scope.end)
            return;
          $scope.contents = [];
          $scope.lectures.forEach(function (lecture) {
            if (!lecture.sideMenu)
              return;
            getLectures(lecture.id);
          });
        }

        function getLectures(lectureId) {
          Content.getList({
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
