angular.module('clientApp')
  .directive('affix', function ($timeout) {
    return {
      restrict: 'A',
      link: function (s, e, a) {
        $timeout(function () {
          $(e).affix({
            offset: a.affix
          });
        });
      }
    };
  });
