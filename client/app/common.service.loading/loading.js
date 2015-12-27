(function () {
  var scope;
  angular.module('clientApp')
    .service('Loading', Loading)
    .directive('loading', function () {
      return {
        restrict: 'E',
        scope: {},
        template: '<img ng-show="Loading.loading" class="fade-in-out" src="/resource/loading.gif">',
        controller: function (Loading, $scope) {
          $scope.Loading = Loading;
          scope = $scope;
        }
      };
    });
  function Loading() {
  }

  Loading.prototype.start = function () {
    this.loading = true;
  };

  Loading.prototype.end = function () {
    this.loading = false;
  };
})();
