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
    controller: function ($scope, $sce) {
      $scope.body = function () {
        return $sce.trustAsHtml($scope.ngModel);
      };
      $scope.save = function () {
        $scope.mod = false;
        if (typeof $scope.modSave !== "function")
          return;
        $scope.modSave();
      };
    }
  };
});
