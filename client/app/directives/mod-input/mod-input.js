angular.module('clientApp').directive('modInput', function () {
  return {
    restrict: 'E',
    templateUrl: '/directives/mod-input/mod-input.html',
    scope: {
      ngModel: '=',
      placeholder: '@'
    },
    controller: function ($scope) {
    }
  }
});
