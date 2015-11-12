angular.module('clientApp')
  /* @ngInject */
  .directive('autoComplete', function ($timeout, http) {
    return {
      restrict: 'E',
      scope: {
        src: '@',
        ngModel: '=',
        placeholder: '@',
        template: '@',
        action: '='
      },
      templateUrl: '/common.directive.auto-complete/auto-complete.html',
      link: function (s) {
        var ajax;
        s.result = [];
        s.index = 0;

        s.select = function (each) {
          s.ngModel = each;
          s.selecting = false;
          s.keyword = '';
          if (!s.action)
            return;
          if (typeof s.action === "function") {
            s.action(each);
          }
          s.$eval(s.action);
        };

        s.search = function (keyword) {
          $timeout.cancel(ajax);
          ajax = $timeout(function () {
            http.get(s.src, {keyword: keyword}).then(function (result) {
              s.result = result;
            });
          });
        };
      }
    };
  });
