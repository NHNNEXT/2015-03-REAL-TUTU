angular.module('clientApp').directive('modTextarea', function () {
  return {
    restrict: 'E',
    templateUrl: '/common.directive.mod-textarea/mod-textarea.html',
    scope: {
      ngModel: '=',
      placeholder: '@',
      modRight: '=',
      modSave: '='
    },
    /* @ngInject */
    controller: function ($scope, $sce) {
      $scope.save = function () {
        $scope.mod = false;
        if (typeof $scope.modSave !== "function")
          return;
        $scope.modSave();
      };
    }
  };
});
