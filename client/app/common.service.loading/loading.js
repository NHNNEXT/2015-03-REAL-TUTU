angular.module('clientApp')
  .service('Loading', function () {
    this.start = function () {
      this.loading = true;
    };

    this.end = function () {
      this.loading = false;
    };

  })
  .directive('loading', function () {
    return {
      restrict: 'E',
      scope: {},
      template: '<md-progress-circular ng-show="Loading.loading" md-mode="indeterminate"></md-progress-circular>',
      controller: function (Loading, $scope) {
        $scope.Loading = Loading;
      }
    };
  });
