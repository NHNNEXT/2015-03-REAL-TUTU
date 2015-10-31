'use strict';

/**
 * @ngdoc directive
 * @name clientApp.directive:menu
 * @description
 * # menu
 */
angular.module('clientApp')
  .directive('menu', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        state: '@',
        name: '@'
      },
      template: "<li ng-class=\"{active:$state.current.name.match(state)}\"><a ui-sref='{{state}}'>{{name}}</a></li>",
      replace: true,
      link: function postLink(scope, element) {
        scope.$state = $state;
      }
    };
  });
