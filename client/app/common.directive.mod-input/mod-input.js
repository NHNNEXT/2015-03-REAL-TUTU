angular.module('clientApp').directive('modInput', function () {
  return {
    restrict: 'E',
    templateUrl: '/common.directive.mod-input/mod-input.html',
    scope: {
      ngModel: '=',
      placeholder: '@',
      maxlength: '@',
      modRight: '=',
      modSave: '='
    },
    /* @ngInject */
    controller: function ($scope) {
      $scope.save = function () {
        $scope.mod = false;
        if (typeof $scope.modSave !== "function")
          return;
        $scope.modSave();
      };
    }
  };
});