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
        $scope.body = {height: $scope.height};

        if (!$scope.start || !$scope.end) {
          var start = $scope.start = new Date();
          var end = $scope.end = new Date();
          start.setDate(start.getDate() - 7);
          end.setDate(end.getDate() + 3);
        }
        var now = new Date();
        $scope.nowCovered = function () {
          return $scope.start < now && now < $scope.end;
        };

        $scope.now = function () {
          return {left: calculateLeft(now)};
        };

        $scope.valid = function (contents) {
          var result = [];
          contents.forEach(function (content) {
            if ($scope.start < content.startTime && content.startTime < $scope.end ) {
              result.push(content);
              return;
            }
            if ($scope.start < content.endTime && content.endTime < $scope.end ) {
              result.push(content);
            }
          });
          return result;
        };


        $scope.getDay = function (position) {
          return new Date($scope.start.getTime() + (($scope.end - $scope.start) / 100 * position)).getDateString();
        };

        function getContentsLength() {
          if (!$scope.contents)
            return 1;
          return $scope.contents.length;
        }

        $scope.left = function (date, index) {
          var divider = getContentsLength();
          var style = {};
          style.left = calculateLeft(date);
          style.position = 'absolute';
          if (index !== undefined)
            style.top = (90 / divider) * (index % divider) + 5 + '%';
          return style;
        };

        $scope.range = function (content, index) {
          var divider = getContentsLength();
          var style = {};
          style.left = calculateLeft(content.startTime);
          style.width = calculateLeft(content.endTime - content.startTime);
          style.position = 'absolute';
          style.top = (90 / divider) * (index % divider) + 5 + '%';
          return style;
        };


        function calculateLeft(date) {
          var left = (((date - $scope.start) / ($scope.end - $scope.start)) * 100);
          if (left < 0)
            return 0;
          if (left > 100)
            return '100%';
          return left + '%';
        }

      }
    }
      ;
  })
;
