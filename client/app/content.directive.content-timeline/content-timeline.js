angular.module('clientApp')
  .directive('contentTimeline', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        start: '=',
        end: '='
      },
      templateUrl: '/content.directive.content-timeline/content-timeline.html',
      controller: function ($scope) {
        var now = new Date();
        $scope.nowCovered = function () {
          return $scope.start < now && now < $scope.end;
        };

        $scope.now = function () {
          return {left: calculateLeft(now)};
        };


        $scope.getDay = function (position) {
          return new Date($scope.start.getTime() + (($scope.end - $scope.start) / 100 * position)).getDateString();
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
    }
      ;
  })
;
