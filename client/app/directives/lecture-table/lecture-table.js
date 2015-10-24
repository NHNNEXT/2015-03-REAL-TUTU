angular.module('clientApp')
  .directive('lectureTable', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        lectures: '=',
        columns: '='
      },
      templateUrl: '/directives/lecture-table/lecture-table.html',
      controller: function ($scope) {
        $scope.key = 'name';

        $scope.setKey = function (k) {
          $scope.key = k;
        };

        if ($scope.columns === undefined) {
          $scope.columns = ['year', 'semester', 'name', 'classNo', 'type', 'credit', 'prof', 'time'];
        }

        $scope.headers = {
          year: "년도",
          semester: "학기",
          name: "강의명",
          classNo: "분반",
          type: "구분",
          credit: "학점",
          prof: "담당교수",
          time: "시간"
        };

        $scope.sort = function (val) {
          if (val === $scope.order) {
            $scope.reverse = !$scope.reverse;
          }
          $scope.order = val;
        }

      }
    };
  });
