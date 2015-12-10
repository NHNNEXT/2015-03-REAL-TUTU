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
