angular.module('clientApp')
  .directive('lectureTable', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        lectures: '=',
        columns: '=',
        query: '='
      },
      templateUrl: '/lecture/directive/lecture-table/lecture-table.html',
      controller: function ($scope, majorTypes) {
        $scope.key = 'name';
        $scope.$watch('lectures', function (lectures) {
          if (!lectures)
            return;
          lectures.forEach(function (lecture) {
            if (majorTypes[lecture.majorType])
              lecture.majorType = majorTypes[lecture.majorType];
          });
        });
        $scope.setKey = function (k) {
          $scope.key = k;
        };
      }
    };
  });
