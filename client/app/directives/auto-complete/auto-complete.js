angular.module('clientApp')
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
      templateUrl: '/directives/auto-complete/auto-complete.html',
      link: function (s, e, a) {
        angular.element($('body')).bind('click', function () {
          s.selecting = false;
          s.$apply();
        });
        var ajax;
        s.result = [];
        s.index = 0;
        s.select = function (each) {
          s.ngModel = each;
          s.selecting = false;
          s.keyword = '';
          if (typeof s.action === "function") {
            s.action(each);
          }
        };
        s.hover = function (i) {
          s.index = i;
        };
        e.bind('keypress keydown', function (e) {
          s.selecting = true;
          if (e.keyCode === 38) {
            s.index--;
            if (s.index < 0)
              s.index = s.result.length - 1;
            s.$apply();
            return;
          }
          if (e.keyCode === 40) {
            s.index++;
            if (s.index > s.result.length - 1)
              s.index = 0;
            s.$apply();
            return;
          }
          if (e.keyCode === 13) {
            s.select(s.result[s.index]);
          }
          if (!s.keyword)
            return;
          $timeout.cancel(ajax);
          ajax = $timeout(function () {
            http.get(s.src, {keyword: s.keyword}, function (result) {
              s.result = result;
            });
          }, 300);
        });
      }
    };
  });
