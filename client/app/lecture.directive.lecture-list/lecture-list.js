angular.module('clientApp')
  .directive('lectureList', function () {
    return {
      restrict: 'E',
      scope: {
        lectures: '=',
        query: '='
      },
      templateUrl: '/lecture.directive.lecture-list/lecture-list.html',
      /* @ngInject */
      controller: function ($scope, types) {
        $scope.majorTypes = types.majorTypes;
      }
    };
  });
