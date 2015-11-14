angular.module('clientApp')
  .directive('inputDatepicker', function ($document) {
    return {
      restrict: 'E',
      scope: {
        ngModel: '=',
        placeholder: '@'
      },
      templateUrl: '/common.directive.input-datepicker/input-datepicker.html',
      link: function (s, elem) {
        var handler = function (e) {
          e.stopPropagation();
        };
        elem.on('click', handler);

        $document.on('click', hide);

        function hide(){
          s.open = false;
          s.$apply();
        }
      }
    };
  });
