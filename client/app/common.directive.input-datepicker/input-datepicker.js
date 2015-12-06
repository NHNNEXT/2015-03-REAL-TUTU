angular.module('clientApp')
  .factory('inputDatePickers', function () {
    return [];
  })
  .directive('inputDatepicker', function ($document, inputDatePickers) {
    return {
      restrict: 'E',
      scope: {
        ngModel: '=',
        placeholder: '@',
        mode: '@'
      },
      templateUrl: '/common.directive.input-datepicker/input-datepicker.html',
      link: function (s, elem) {

        inputDatePickers.push(s);

        var handler = function (e) {
          inputDatePickers.forEach(function (scope) {
            scope.open = false;
          });
          e.stopPropagation();
        };
        elem.on('click', handler);
        $document.on('click', hide);


        function hide() {
          s.open = false;
          s.$apply();
        }
      }
    };
  });
