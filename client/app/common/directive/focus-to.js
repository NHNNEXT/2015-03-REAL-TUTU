var search;
angular.module('clientApp')
  .directive('focusTo', function ($timeout) {
    return {
      restrict: 'A',
      link: function postLink(scope, element, attrs) {
        element.bind('click', function () {
          $timeout(function () {
            search = document.querySelector(attrs.focusTo);
            console.log(search);
            if (search == null)
              search.focus();
          },1000);
        });
      }
    };
  });
