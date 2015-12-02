angular.module('clientApp')
  .directive('relativeContents', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        readonly: '='
      },
      templateUrl: '/content.directive.relative-contents/relative-contents.html',
      bindToController: true,
      controllerAs: 'ctrl',
      controller: function (http, $scope) {
        var self = this;
        this.contents = [$scope.content];
        this.querySearch = function (keyword) {
          return http.get('/api/v1/content', {keyword: keyword}).then(function (result) {
            if (self.result === null || self.result === "null")
              return;
            return result;
          });
        };
      }
    };
  });
