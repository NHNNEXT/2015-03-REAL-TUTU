angular.module('clientApp')
  .directive('affix', function ($timeout) {
    return {
      restrict: 'A',
      link: function postLink(s, e, a) {
        console.log(1);
        $timeout(function () {
          $(e).affix({
            offset: a.affix
          });
        });
      }
    };
  });
