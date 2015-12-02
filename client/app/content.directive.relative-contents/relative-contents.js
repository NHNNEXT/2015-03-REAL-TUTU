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
      controller: function (http) {
        var self = this;
        this.querySearch = function (keyword) {
          return http.get('/api/v1/tag', {keyword: keyword}).then(function (result) {
            if (self.result === null || self.result === "null")
              return;
            return result;
          });
        };
      }
    };
  });
