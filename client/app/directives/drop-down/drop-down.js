angular.module('clientApp')
  .directive('dropDown', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        options: '=',
        ngModel: '=',
        placeholder: '@',
        names: '='
      },
      templateUrl: '/directives/drop-down/drop-down.html',
      controller: function ($scope) {

        $scope.setSelected = function (option) {
          $scope.ngModel = option;
        }

      }
    };
  });
