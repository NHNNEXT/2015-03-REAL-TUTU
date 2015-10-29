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
            link: function postLink(scope, element) {
                scope.$state = $state;
                var html = "<li ng-class=\"{active:$state.current.name.match('" + scope.state + "')}\"><a ui-sref='" + scope.state + "'>" + scope.name + "</a></li>";
                var e = $compile(html)(scope);
                element.replaceWith(e);
            }
        };
    });
