angular.module('clientApp')
  .service('Loading', function ($timeout) {
    this.start = function () {
      this.loading = true;
    };

    this.end = function () {
      var self = this;
      $timeout(function () {
        self.loading = false;
      }, 800);
    };

  })
  .directive('loading', function () {
    return {
      restrict: 'E',
      scope: {},
      template: '<img ng-show="Loading.loading" class="fade-in-out" src="/resource/loading.gif">',
      controller: function (Loading, $scope) {
        $scope.Loading = Loading;
      }
    };
  });
