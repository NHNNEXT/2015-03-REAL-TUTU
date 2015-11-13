angular.module('clientApp')
  .directive('inputDatepicker', function () {
    return {
      restrict: 'E',
      scope: {
        ngModel: '='
      },
      templateUrl: '/common.directive.input-datepicker/input-datepicker.html'
      ///* @ngInject */
      //controller: function ($scope, http, user, responseCode, $timeout) {
      //}
    };
  });
