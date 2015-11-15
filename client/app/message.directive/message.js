angular.module('clientApp').directive('message',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/message.directive/message.html',
      scope: {
        message: '='
      }, controller: function ($scope, $location) {
        $scope.move = function () {
          $scope.message.reading();
          $location.path($scope.message.url);
        };
      }
    };
  });
