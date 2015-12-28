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
      template: '<img ng-show="Loading.loading" class="fade-in-out" src="/resource/loading.gif" ng-style="style">',
      controller: function (Loading, $scope, $timeout, $interval) {
        $scope.Loading = Loading;

        updateStyle();

        function updateStyle() {
          var result = {};
          var degree = Math.random() * 360;
          result.transform = 'translateX(-50%) translateY(-50%) rotate(' + degree + 'deg)';
          result['-ms-transform'] = 'translateX(-50%) translateY(-50%) rotate(' + degree + 'deg)';
          result['-webkit-transform'] = 'translateX(-50%) translateY(-50%) rotate(' + degree + 'deg)';
          $scope.style = result;
        }

        $interval(updateStyle, 1000);

      }
    };
  });
