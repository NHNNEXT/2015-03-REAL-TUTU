angular.module('clientApp')
  .directive('like', function () {
    return {
      restrict: 'E',
      scope: {
        value: '=',
        target: '='
      },
      templateUrl: '/common/directive/like/like.html',
      controller: function ($scope) {
        if (!$scope.value)
          $scope.value = 0;
      }
    };
  });
