'use strict';

/**
 * @ngdoc directive
 * @name clientApp.directive:ngRegex
 * @description
 * # ngRegex
 */
angular.module('clientApp')
  .directive('sort', function ($compile) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        var order = attrs.order === undefined ? "order" : attrs.order;
        var reverse = attrs.reverse === undefined ? "reverse" : attrs.reverse;
        element.bind('click', function () {
          if (scope[order] == attrs.sort)
            scope[reverse] = !scope[reverse];
          scope[order] = attrs.sort;
          if (!scope.$$phase)
            scope.$apply();
        });
      }
    };
  });
