angular.module('clientApp')
  .directive('inputDatepicker', function () {
    return {
      restrict: 'E',
      scope: {
        ngModel: '=',
        placeholder: '@',
        mdMaxDate: '=',
        mdMinDate: '='
      },
      templateUrl: '/common.directive.input-datepicker/input-datepicker.html'
    };
  });
